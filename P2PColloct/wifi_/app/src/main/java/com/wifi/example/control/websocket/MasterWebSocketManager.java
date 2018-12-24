package com.wifi.example.control.websocket;

import android.util.Log;

import com.koushikdutta.async.AsyncNetworkSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.socks.library.KLog;
import com.wifi.example.control.ControlConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2017 SpeakIn.Inc
 * Created by west on 2017/9/28.
 */

public class MasterWebSocketManager {

    private static final String TAG = MasterWebSocketManager.class.getSimpleName();
    private final List<SlaveClientSocket> socketClients = new ArrayList<>();
    private int clientMaxCount = 3;

    private AsyncHttpServer server = new AsyncHttpServer();
    private MasterSocketManagerCallback masterSocketManagerCallback;
    private AsyncHttpServer.WebSocketRequestCallback callback = new AsyncHttpServer.WebSocketRequestCallback() {
        @Override
        public void onConnected(final WebSocket webSocket, AsyncHttpServerRequest request) {
            KLog.i(TAG, "request" + request.toString());
            AsyncNetworkSocket asyncNetworkSocket = (AsyncNetworkSocket) webSocket.getSocket();
            KLog.i(TAG, asyncNetworkSocket.getRemoteAddress().getAddress() + ":" + asyncNetworkSocket.getRemoteAddress().getPort() + " connected");
            if (socketClients.size() >= clientMaxCount) {
                webSocket.close();
                return;
            }

            SlaveClientSocket found = null;
            for (SlaveClientSocket ws : socketClients) {
                AsyncNetworkSocket anws = (AsyncNetworkSocket) ws.mSocket.getSocket();
                if (asyncNetworkSocket.getRemoteAddress().getHostName().equals(anws.getRemoteAddress().getHostName())) {
                    found = ws;
                    break;
                }
            }
            if (found == null) {
                socketClients.add(new SlaveClientSocket(webSocket));
                if (masterSocketManagerCallback != null)
                    masterSocketManagerCallback.onClientConnected(webSocket);
            } else {
                KLog.i(TAG, "slave already connect");
            }
        }
    };

    public MasterWebSocketManager(int count) {
        clientMaxCount = count;
    }

    public void setMasterSocketManager(MasterSocketManagerCallback callback) {
        masterSocketManagerCallback = callback;
    }

    public void start() {
        server.websocket("/", ControlConstants.PROTOCOL, callback);
        server.listen(ControlConstants.SERVER_SOCKET_PORT);
        server.setErrorCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    ex.printStackTrace();
                    Log.e(TAG, "Error");
                    if (masterSocketManagerCallback != null)
                        masterSocketManagerCallback.onServerError(ex);
                }
            }
        });
        KLog.i(TAG, "start server and listen");
    }

    public void stop() {
        KLog.i(TAG, "stop");

        for (SlaveClientSocket clientSocket : socketClients) {
            clientSocket.mSocket.close();
        }
        socketClients.clear();

        if (server != null) {
            server.stop();
        }
    }

    public void sendMessage(String message) {
        for (SlaveClientSocket clientSocket : socketClients) {
            clientSocket.mSocket.send(message);
        }
    }

    public int getClientCount() {
        return socketClients.size();
    }

    private String getFilePath() {
        String fileName = System.currentTimeMillis() + ".wav";
        String filePath = ControlConstants.RECEIVE_DIR;
        boolean ret = new File(filePath).mkdirs();
        if (!ret) {
//            Log.e(TAG, "make dir error");
        }
        return filePath + fileName;
    }


    public interface MasterSocketManagerCallback {
        void onServerError(Exception ex);

        void onClientConnected(WebSocket clientSocket);

        void onClientDisconnect(WebSocket clientSocket);

        void onMessageReceive(WebSocket clientSocket, String message);

        void onFileReceive(WebSocket clientSocket, String filePath);
    }

    private class SlaveClientSocket implements CompletedCallback, WebSocket.StringCallback, DataCallback {
        private static final int BUFFER_LEN = 1024 * 8;
        WebSocket mSocket;

        SlaveClientSocket(WebSocket clientSocket) {
            mSocket = clientSocket;
            mSocket.setStringCallback(this);
            mSocket.setClosedCallback(this);
            mSocket.setDataCallback(this);
        }

        @Override
        public void onCompleted(Exception ex) {
            try {
                if (ex != null) {
                    Log.e(TAG, "Error");
                    ex.printStackTrace();
                } else {
                    KLog.i(TAG, "WebSocketClient closed normally.");
                }
            } finally {
                socketClients.remove(this);
                if (masterSocketManagerCallback != null)
                    masterSocketManagerCallback.onClientDisconnect(mSocket);
            }
        }

        @Override
        public void onStringAvailable(String s) {
            AsyncNetworkSocket asyncNetworkSocket = (AsyncNetworkSocket) mSocket.getSocket();
            KLog.i(TAG, "from:" + asyncNetworkSocket.getRemoteAddress().getAddress() + ":" + asyncNetworkSocket.getRemoteAddress().getPort());
            KLog.i(TAG, "msg = " + s);
            if (masterSocketManagerCallback != null) {
                masterSocketManagerCallback.onMessageReceive(mSocket, s);
            }
        }

        @Override
        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            KLog.i(TAG, "onDataAvailable");
            String filePath = getFilePath();
            try {
                FileOutputStream outputStream = new FileOutputStream(new File(filePath));
                long start = System.currentTimeMillis();
                while (bb.hasRemaining()) {
                    int remain = bb.remaining();
                    if (remain > BUFFER_LEN) {
                        outputStream.write(bb.getBytes(BUFFER_LEN));
                    } else {
                        outputStream.write(bb.getBytes(remain));
                    }
                }
                outputStream.flush();
                outputStream.close();
                KLog.i(TAG, "receive file:" + filePath);
                KLog.i(TAG, "spend " + (System.currentTimeMillis() - start));
                if (masterSocketManagerCallback != null) {
                    masterSocketManagerCallback.onFileReceive(mSocket, filePath);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
