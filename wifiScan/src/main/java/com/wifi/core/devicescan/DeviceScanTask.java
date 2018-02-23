package com.wifi.core.devicescan;

import android.text.TextUtils;
import android.util.Log;

import com.wifi.App;
import com.wifi.DeviceInfo;
import com.wifi.R;
import com.wifi.util.Constant;
import com.wifi.util.NetworkUtil;

import java.io.IOException;

class DeviceScanTask {
    private static final String tag = DeviceScanTask.class.getSimpleName();
    Thread mThread;
    DeviceScanRunnable mRunnable;
    private DeviceInfo mIpMac;
    private DeviceScanHandler mDeviceScanHandler;


    DeviceScanTask() {
        mRunnable = new DeviceScanRunnable();
    }


    void setParams(DeviceInfo deviceInfo, DeviceScanHandler handler) {
        this.mIpMac = deviceInfo;
        this.mDeviceScanHandler = handler;
    }

    private String parseHostInfo(String mac) {
        return Manufacture.getInstance().getManufacture(mac, App.getInstance().getApplicationContext());
    }

    private class DeviceScanRunnable implements Runnable {
        public void run() {
            if (NetworkUtil.isPingOk(mIpMac.mIp) ||
                    NetworkUtil.isAnyPortOk(mIpMac.mIp)) {
                String manufacture = parseHostInfo(mIpMac.mMac); //解析机器名称
                Log.e(tag, "the device is in wifi : " + mIpMac.toString() + "" +
                        " manufacture = " + manufacture);
                if (!TextUtils.isEmpty(manufacture)) {
                    mIpMac.mManufacture = manufacture;
                }

                try {
                    NetBios nb = new NetBios(mIpMac.mIp);
                    String deviceName = nb.getNbName();
                    Log.d(tag, "device name = " + deviceName);
                    if (!TextUtils.isEmpty(deviceName)) {
                        mIpMac.mDeviceName = deviceName;
                    } else {
                        mIpMac.mDeviceName = App.getInstance()
                                .getResources()
                                .getString(
                                        R.string.unknown);
                    }
                } catch (IOException e) {
                    mIpMac.mDeviceName = App.getInstance()
                            .getResources()
                            .getString(
                                    R.string.unknown);
                    e.printStackTrace();
                }

                if (mDeviceScanHandler != null) {
                    mDeviceScanHandler.sendMessage(
                            mDeviceScanHandler.obtainMessage(
                                    Constant.MSG.SCAN_ONE, mIpMac));
                }
            }
        }
    }
}
