package xyz.viseator.anonymouscard.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.UDPDataPackage;

/**
 * Created by viseator on 2016/12/23.
 * Wudi
 * viseator@gmail.com
 */

public class TcpClient {
    private static final String TAG = "wudi TCPClient";
    public static int SERVER_PORT = 7889;
    private Thread thread;
    private String ipAddress;
    private UDPDataPackage udpDataPackage;
    private Handler handler;


    class SendData implements Runnable {
        @Override
        public void run() {
            Socket socket = null;
            try {
                socket = new Socket(ipAddress, SERVER_PORT);
                socket.setReuseAddress(true);
                socket.setKeepAlive(true);
                socket.setSoTimeout(5000);

                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(udpDataPackage);

                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                DataPackage dataPackage = (DataPackage) objectInputStream.readObject();

                Message msg = new Message();
                msg.what = SERVER_PORT;
                msg.obj = dataPackage;
                handler.sendMessage(msg);
            } catch (SocketTimeoutException e) {
                Log.d(TAG, "run: Time out");
                try {
                    if (socket != null) socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                sendRequest(ipAddress, udpDataPackage, handler);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendRequest(String ipAddress, UDPDataPackage udpDataPackage, Handler handler) {
        this.ipAddress = ipAddress;
        this.udpDataPackage = udpDataPackage;
        this.handler = handler;
        thread = new Thread(new SendData());
        thread.start();
    }
}
