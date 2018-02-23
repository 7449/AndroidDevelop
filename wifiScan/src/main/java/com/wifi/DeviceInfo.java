package com.wifi;


public class DeviceInfo {
    public String mIp;
    public String mMac;
    public String mManufacture;
    public String mDeviceName;

    public DeviceInfo(String ip, String mac) {
        this.mIp = ip;
        this.mMac = mac;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceInfo that = (DeviceInfo) o;
        return (mIp != null ? mIp.equals(that.mIp) : that.mIp == null) && (mMac != null ? mMac.equals(that.mMac) : that.mMac == null) && (mManufacture != null ? mManufacture.equals(that.mManufacture) : that.mManufacture == null) && (mDeviceName != null ? mDeviceName.equals(that.mDeviceName) : that.mDeviceName == null);
    }

    @Override
    public int hashCode() {
        int result = mIp != null ? mIp.hashCode() : 0;
        result = 31 * result + (mMac != null ? mMac.hashCode() : 0);
        result = 31 * result + (mManufacture != null ? mManufacture.hashCode() : 0);
        result = 31 * result + (mDeviceName != null ? mDeviceName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "mIp='" + mIp + '\'' +
                ", mMac='" + mMac + '\'' +
                ", mManufacture='" + mManufacture + '\'' +
                ", mDeviceName='" + mDeviceName + '\'' +
                '}';
    }
}
