package com.wifi.example.control.websocket;

import android.text.TextUtils;
import android.util.Log;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.socks.library.KLog;
import com.wifi.example.control.ControlConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Copyright 2017 SpeakIn.Inc
 * Created by west on 2017/9/29.
 */

public class SlaveWebSocketManager {

    private static final String TAG = SlaveWebSocketManager.class.getSimpleName();
    private static final int BUFFER_LEN = 8 * 1024;
    private SlaveSocketManagerCallback slaveSocketManagerCallback;
    private String serverIp;
    private int serverPort;
    private WebSocket socket;
    private WebSocket.StringCallback stringCallback = new WebSocket.StringCallback() {
        @Override
        public void onStringAvailable(String s) {
            KLog.i(TAG, "msg from server: " + s);
            if (slaveSocketManagerCallback != null) slaveSocketManagerCallback.onReceiveMessage(s);
        }
    };
    private DataCallback dataCallback = new DataCallback() {
        @Override
        public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
            KLog.i(TAG, "onDataAvailable");
        }
    };
    private CompletedCallback closeCallback = new CompletedCallback() {
        @Override
        public void onCompleted(Exception ex) {
            if (ex != null) {
                ex.printStackTrace();
                Log.e(TAG, "close error = " + ex.getMessage());
            }
            if (slaveSocketManagerCallback != null)
                slaveSocketManagerCallback.onMasterDisconnect(socket, ex);
            socket = null;
        }
    };

    public SlaveWebSocketManager(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void setSlaveSocketManagerCallback(SlaveSocketManagerCallback callback) {
        slaveSocketManagerCallback = callback;
    }

    public void startConnect() {
        if (isConnected()) {
            return;
        }
        String url = "ws://" + serverIp + ":" + serverPort;
        KLog.i(TAG, "start connect to url=" + url);
        AsyncHttpClient.getDefaultInstance().websocket(url, ControlConstants.PROTOCOL, new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, final WebSocket webSocket) {
                if (ex != null) {
                    ex.printStackTrace();
                    Log.e(TAG, "connect error = " + ex.getMessage());
                    if (slaveSocketManagerCallback != null)
                        slaveSocketManagerCallback.onMasterConnected(null, ex);
                    return;
                }
                KLog.i(TAG, "connected to server");

                socket = webSocket;
                socket.setStringCallback(stringCallback);
                socket.setClosedCallback(closeCallback);
                socket.setDataCallback(dataCallback);
                socket.send("confirm from client");

                if (slaveSocketManagerCallback != null)
                    slaveSocketManagerCallback.onMasterConnected(socket, null);
            }
        });
    }

    public boolean sendMessage(String message) {
        if (!isConnected()) {
            KLog.i(TAG, "not connected");
            return false;
        }
        if (TextUtils.isEmpty(message)) return false;

        socket.send(message);
        return true;
    }

    public void sendFile(final String filePath) {
        if (filePath == null || !(new File(filePath).exists())) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                KLog.i(TAG, "send file " + filePath);
                long start = System.currentTimeMillis();
                try {
                    File file = new File(filePath);
                    KLog.i(TAG, "file len=" + file.length());
                    FileInputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[BUFFER_LEN];
                    int read;
                    while ((read = inputStream.read(buffer)) != -1) {
                        if (read == BUFFER_LEN) {
                            baos.write(buffer);
                        } else {
                            byte[] bs2 = new byte[read];
                            System.arraycopy(buffer, 0, bs2, 0, read);
                            baos.write(bs2);
                        }
                    }

                    byte[] payload = baos.toByteArray();

                    socket.send(payload);
                    KLog.i(TAG, "send file done");
                    KLog.i(TAG, "spend " + (System.currentTimeMillis() - start));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void stopConnect() {
        if (socket != null) {
            socket.close();
            socket = null;
        }
    }

    public boolean isConnected() {
        return socket != null;
    }

    public interface SlaveSocketManagerCallback {
        void onMasterConnected(WebSocket socket, Exception ex);

        void onMasterDisconnect(WebSocket socket, Exception ex);

        void onReceiveMessage(String message);
    }
}
