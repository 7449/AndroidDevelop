package com.wifi.util;


public class Constant {
    public static final int COUNT = 255;

    public static final int SCAN_COUNT = 3;

    /***
     * 137端口的主要作用是在局域网中提供计算机的名字或IP地址查询服务
     */
    public static final int NET_BIOS_PORT = 137;

    public interface MSG {
        int START = 0;
        int STOP = -1;
        int SCAN_ONE = 1;
        int SCAN_OVER = 2;
    }

}
