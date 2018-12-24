package com.wifi.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.util.SparseArray;

import com.wifi.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Enumeration;

public class NetworkUtil {

    private static final String tag = NetworkUtil.class.getSimpleName();
    @SuppressLint("UseSparseArrays")
    private static SparseArray<Integer> mChannelFrequency = new SparseArray<>();

    static {
        mChannelFrequency.put(1, 2412);
        mChannelFrequency.put(2, 2417);
        mChannelFrequency.put(3, 2422);
        mChannelFrequency.put(4, 2427);
        mChannelFrequency.put(5, 2432);
        mChannelFrequency.put(6, 2437);
        mChannelFrequency.put(7, 2442);
        mChannelFrequency.put(8, 2447);
        mChannelFrequency.put(9, 2452);
        mChannelFrequency.put(10, 2457);
        mChannelFrequency.put(11, 2462);
        mChannelFrequency.put(12, 2467);
        mChannelFrequency.put(13, 2472);
        mChannelFrequency.put(14, 2484);

        mChannelFrequency.put(36, 5180);
        mChannelFrequency.put(40, 5200);
        mChannelFrequency.put(44, 5220);
        mChannelFrequency.put(48, 5240);
        mChannelFrequency.put(52, 5260);
        mChannelFrequency.put(56, 5280);
        mChannelFrequency.put(60, 5300);
        mChannelFrequency.put(64, 5320);
        mChannelFrequency.put(100, 5500);
        mChannelFrequency.put(104, 5520);
        mChannelFrequency.put(108, 5540);
        mChannelFrequency.put(112, 5560);
        mChannelFrequency.put(116, 5580);
        mChannelFrequency.put(120, 5600);
        mChannelFrequency.put(124, 5620);
        mChannelFrequency.put(128, 5640);
        mChannelFrequency.put(132, 5660);
        mChannelFrequency.put(136, 5680);
        mChannelFrequency.put(140, 5700);
        mChannelFrequency.put(149, 5745);
        mChannelFrequency.put(153, 5765);
        mChannelFrequency.put(157, 5785);
        mChannelFrequency.put(161, 5805);
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo.isConnected();

    }

    private static WifiManager getWifiManager() {
        return (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取设备连接的网关的ip地址
     */
    public static String getGateWayIp() {
        String gatewayIp = null;
        DhcpInfo dhcpInfo = getWifiManager().getDhcpInfo();
        if (dhcpInfo != null) {
            gatewayIp = Int2String(dhcpInfo.gateway);
        }
        return gatewayIp;
    }

    /**
     * 获取设备的mac地址
     */
    @SuppressLint("HardwareIds")
    public static String getLocalMac() {
        return getWifiManager().getConnectionInfo().getMacAddress();
    }

    /**
     * 获取设备的ip地址
     */
    public static String getLocalIp() {
        String localIp = null;
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface networkInterface = en.nextElement();
                Enumeration<InetAddress> inetAddresses
                        = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        localIp = inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return localIp;
    }

    private static String Int2String(int IP) {
        String ipStr = "";
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            ipStr += String.valueOf(0xFF & IP);
            ipStr += ".";
            ipStr += String.valueOf(0xFF & IP >> 8);
            ipStr += ".";
            ipStr += String.valueOf(0xFF & IP >> 16);
            ipStr += ".";
            ipStr += String.valueOf(0xFF & IP >> 24);
        } else {

            ipStr += String.valueOf(0xFF & IP >> 24);
            ipStr += ".";
            ipStr += String.valueOf(0xFF & IP >> 16);
            ipStr += ".";
            ipStr += String.valueOf(0xFF & IP >> 8);
            ipStr += ".";
            ipStr += String.valueOf(0xFF & IP);
        }

        return ipStr;
    }

    /**
     * 是否ping通
     */
    public static boolean isPingOk(String ip) {
        try {
            Process p = Runtime.getRuntime()
                    .exec("/system/bin/ping -c 10 -w 4 " + ip);
            if (p == null) {
                return false;
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("bytes from")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 该ip的端口某些端口是否打开
     * 22 linux ssh端口
     * 80和8081 http 端口
     * 135 远程打开对方的telnet服务器
     * 137 在局域网中提供计算机的名字或OP地址查询服务，一般安装了NetBIOS协议后，就会自动开放
     * 139 Windows获得NetBIOS/SMB服务
     * 445 局域网中文件的共享端口
     * 3389 远程桌面服务端口
     * 1900 ssdp协议
     * 5351 AppleBonjour、回到我的 Mac
     * 5353 Apple Bonjour、AirPlay、家庭共享、打印机查找、回到我的 Mac
     * 62078 Apple的一个端口
     * see link{https://support.apple.com/zh-cn/HT202944}
     */
    public static boolean isAnyPortOk(String ip) {
        int portArray[] = {22, 80, 135, 137, 139, 445, 3389, 4253, 1034, 1900,
                993, 5353, 5351, 62078};
        Selector selector;
        for (int aPortArray : portArray) {
            try {
                Log.d(tag, "is any port ok ? ip = " + ip + " port =" + aPortArray);
                //tcp port detection
                selector = Selector.open();
                SocketChannel channel = SocketChannel.open();
                SocketAddress address = new InetSocketAddress(ip, aPortArray);
                channel.configureBlocking(false);
                channel.connect(address);
                channel.register(selector, SelectionKey.OP_CONNECT, address);
                if (selector.select(500) != 0) {
                    Log.d(tag, ip + "is any port ok port ? " + aPortArray + " tcp is ok");
                    selector.close();
                    return true;
                } else {
                    selector.close();
                }
            } catch (IOException e) {
                Log.e(tag, e.getMessage());
            }
        }
        return false;
    }
}
