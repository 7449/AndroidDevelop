package xyz.viseator.anonymouscard.data;

import java.io.Serializable;

/**
 * Created by viseator on 2016/12/21.
 * Wudi
 * viseator@gmail.com
 */

public class UDPDataPackage implements Serializable {
    final long serialVersionUID = 66666666L;
    private String ipAddress;
    private String macAddress;
    private String title;
    private String id;

    public UDPDataPackage(DataPackage dataPackage) {
        ipAddress = dataPackage.getIp();
        macAddress = dataPackage.getMac();
        title = dataPackage.getTitle();
        id = dataPackage.getId();
    }

    public String getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getTitle() {
        return title;
    }
}
