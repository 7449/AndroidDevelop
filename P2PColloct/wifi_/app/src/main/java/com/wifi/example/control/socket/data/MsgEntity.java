package com.wifi.example.control.socket.data;

public class MsgEntity {

    public static final int TYPE_TEXT = 1;
    public static final int TYPE_VOICE = 2;

    /**
     * 用户名
     */
    public String userName;

    /**
     * 日期
     */
    public String date;

    /**
     * 是否是自己发送
     */
    public boolean isSelf;

}
