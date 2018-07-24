package xyz.viseator.anonymouscard.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by yanhao on 16-12-20.
 */

public class ComUtil {
    public static final String CHARSET = "utf-8";
    private static final String BROADCAST_IP = "224.0.1.2"; //IP协议中特殊IP地址，作为一个组，用来集合加入的所有客户端
    public static final int BROADCAST_PORT = 7816; //广播目的端的端口号,客户端的端口号是服务端端口号+1
    private static final int DATA_LEN = 4096;
    private MulticastSocket socket = null;
    private InetAddress broadcastAddress = null;//当前设备在局域网下的IP地址
    byte[] inBuff = new byte[DATA_LEN];
    private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);//用于接受对象的packet
    private DatagramPacket outPacket = null;//用于发送对象的packet
    private Handler handler;
    private static final String TAG = "wudi ComUtil";
    public ComUtil(Handler handler) {
        this.handler = handler;
    }

    // 广播消息的工具方法
    public void broadCast(final byte[] msg) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    outPacket.setData(msg);
                    socket.send(outPacket);
                    Log.d(TAG, "run: Send UDP");
                }
                // 捕捉异常
                catch (IOException ex) {
                    ex.printStackTrace();
                    if (socket != null) {
                        // 关闭该Socket对象
                        socket.close();
                    }
                    Log.d("broadcast error", "发送信息异常，请确认30000端口空闲，且网络链接正常");
                    System.exit(1);
                }
            }
        });
        thread.start();
    }

    class ReadBroad implements Runnable {
        public void run() {
            while (true) {
                try {
                    socket.receive(inPacket);
                    Message message = new Message();
                    message.what = BROADCAST_PORT;
                    message.obj = inBuff;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startRecieveMsg() {
        try {
            socket = new MulticastSocket(BROADCAST_PORT);
            broadcastAddress = InetAddress.getByName(BROADCAST_IP);
            socket.joinGroup(broadcastAddress);
            //socket.setLoopbackMode(false);
            outPacket = new DatagramPacket(new byte[0], 0, broadcastAddress, BROADCAST_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(new ReadBroad());
        thread.start();
    }

    public void closeSocket() {
        socket.close();
    }
}