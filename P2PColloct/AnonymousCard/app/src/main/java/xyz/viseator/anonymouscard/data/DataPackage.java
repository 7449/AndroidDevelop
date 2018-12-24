package xyz.viseator.anonymouscard.data;

import java.io.Serializable;

/**
 * Created by yanhao on 16-12-22.
 */

public class DataPackage implements Serializable {
    final long serialVersionUID = 66666666L;
    private int sign;
    private String mac;
    private String ip;
    private String id;
    private byte[] bitmap;
    private String content;
    private String title;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = mac + "_" + String.valueOf(id);
    }

    public void setId(String id) {
        this.id = id;
    }
    public byte[] getBitmap() {
        return bitmap;
    }


    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
