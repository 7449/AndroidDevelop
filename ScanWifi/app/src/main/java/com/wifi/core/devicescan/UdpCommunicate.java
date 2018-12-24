package com.wifi.core.devicescan;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public abstract class UdpCommunicate {
    private byte[] mBuffer = new byte[1024];

    private DatagramSocket mUdpSocket;

    UdpCommunicate() throws SocketException {
        mUdpSocket = new DatagramSocket();
        mUdpSocket.setSoTimeout(500);
    }

    public abstract String getPeerIp();

    public abstract int getPort();

    public abstract byte[] getSendContent();

    void send() throws IOException {
        byte[] mBytes = getSendContent();
        DatagramPacket dp = new DatagramPacket(mBytes, mBytes.length, InetAddress.getByName(getPeerIp()), getPort());
        mUdpSocket.send(dp);
    }

    DatagramPacket receive() throws IOException {
        DatagramPacket dp = new DatagramPacket(mBuffer, mBuffer.length);
        mUdpSocket.receive(dp);
        return dp;
    }

    void close() {
        if (mUdpSocket != null) {
            mUdpSocket.close();
        }
    }

}
