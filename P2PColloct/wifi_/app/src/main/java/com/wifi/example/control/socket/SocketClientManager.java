package com.wifi.example.control.socket;

import com.wifi.example.control.socket.handle.MsgReceiveHandler;
import com.wifi.example.control.socket.handle.MsgSendHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author keshuangjie
 * @version 1.0
 *          连接管理类
 * @date 2014-12-1 下午7:40:29
 * @package com.jimmy.im.client.socket
 */
public class SocketClientManager {

    private static final SocketClientManager sINSTANCE = new SocketClientManager();
    int serverPort;
    private String serverIP;

    private SocketClientManager() {
    }

    public static SocketClientManager getInstance() {
        return sINSTANCE;
    }

    /**
     * 启动一个socket长连接
     */
    public void startSocket(String serverIp, int serverPort) {
        this.serverIP = serverIp;
        this.serverPort = serverPort;
        new Launcher().start();
    }

//	@Deprecated
//	public void startTextHandlerSocket(){
//		new TextHandlerSocket().start();
//	}
//
//	@Deprecated
//	public void startFileSendSocket() {
//		new FileHandlerSocket(FileHandlerSocket.TYPE_SEND).start();
//	}
//
//	@Deprecated
//	public void startFileReceivelerSocket() {
//		new FileHandlerSocket(FileHandlerSocket.TYPE_RECEIVE).start();
//	}

    private class Launcher extends Thread {

        @Override
        public void run() {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(serverIP, serverPort), 5000);
                socket.setKeepAlive(true);

                new MsgReceiveHandler(socket).start();
                new MsgSendHandler(socket).start();

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
