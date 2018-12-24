package com.wifi.example.control.search;

import android.os.Handler;
import android.os.Looper;

import com.socks.library.KLog;
import com.wifi.example.control.ControlConstants;
import com.wifi.example.utils.IpUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class DeviceBroadcastReceiver {


    private static final int BUFFER_LEN = 1024 * 4;
    private volatile boolean needListen = true;
    private int port = 0;
    private Handler handler;
    private DatagramSocket server = null;
    private BroadcastReceiverCallback callback;

    public DeviceBroadcastReceiver(boolean isSlave) {
        if (!isSlave) {
            port = ControlConstants.MASTER_LISTEN_PORT;
        } else {
            port = ControlConstants.SLAVE_LISTEN_PORT;
        }
        handler = new Handler(Looper.getMainLooper());
        needListen = true;
    }

    public void setBroadcastReceiveCallback(BroadcastReceiverCallback callback) {
        this.callback = callback;
    }

    private void startReceive() throws IOException {
        final DatagramPacket receive = new DatagramPacket(new byte[BUFFER_LEN], BUFFER_LEN);

        server = new DatagramSocket(port);

        KLog.i("---------------------------------");
        KLog.i("start listen ......");
        KLog.i("---------------------------------");

        while (needListen) {
            server.receive(receive);
            byte[] recvByte = Arrays.copyOfRange(receive.getData(), 0, receive.getLength());
            final String receiveMsg = new String(recvByte);
            KLog.i("receive msg:" + receiveMsg);

            final String senderIp = receive.getAddress().getHostAddress();
            String localIP = IpUtil.getHostIP();
            if (senderIp.equals(localIP)) {
                KLog.i("myself,ignore");
                continue;
            }
            KLog.i("hostIp" + receive.getAddress().toString());
            KLog.i("port" + receive.getPort());

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (callback != null) {
                        callback.onReceive(senderIp, receiveMsg);
                    }
                }
            });
        }
        server.disconnect();
        server.close();
        KLog.i("end listen ......");
    }

    public void startBroadcastReceive() {

        if (server != null) {
            server.close();
            server = null;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    startReceive();
                } catch (final IOException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onError(e.getLocalizedMessage());
                            }
                        }
                    });
                    if (server != null) {
                        server.close();
                        server = null;
                    }
                }
            }
        }).start();
    }

    public void stopReceive() {
        needListen = false;
    }

    public interface BroadcastReceiverCallback {
        void onError(String errMsg);

        void onReceive(String senderIp, String message);
    }
}
