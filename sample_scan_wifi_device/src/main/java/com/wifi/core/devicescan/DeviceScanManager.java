package com.wifi.core.devicescan;


import android.os.Handler;
import android.os.Message;

import com.wifi.DeviceInfo;
import com.wifi.core.CustomHandlerThread;
import com.wifi.util.Constant;

import java.lang.ref.WeakReference;


public class DeviceScanManager {

    private DeviceScanManagerHandler mUiHandler;
    private DeviceScanHandler mDeviceScanHandler;
    private CustomHandlerThread mHandlerThread;

    private DeviceScanResult mScanResult;

    public DeviceScanManager() {
        mUiHandler = new DeviceScanManagerHandler(this);
    }

    public void startScan(DeviceScanResult scanResult) {
        mScanResult = scanResult;
        mHandlerThread = new CustomHandlerThread("DeviceScanThread", DeviceScanHandler.class);
        mHandlerThread.start();
        mHandlerThread.isReady();
        mDeviceScanHandler = (DeviceScanHandler) mHandlerThread.getLooperHandler();
        mDeviceScanHandler.init(mUiHandler);
        mDeviceScanHandler.sendMessage(mDeviceScanHandler.obtainMessage(Constant.MSG.START));
    }

    public void stopScan() {
        if (mHandlerThread != null) {
            mDeviceScanHandler.sendMessage(mDeviceScanHandler.obtainMessage(Constant.MSG.STOP));
            mHandlerThread.quit();
            mHandlerThread = null;
        }
    }

    public static class DeviceScanManagerHandler extends Handler {
        private WeakReference<DeviceScanManager> weakReference;

        DeviceScanManagerHandler(DeviceScanManager manager) {
            weakReference = new WeakReference<>(manager);
        }

        @Override
        public void handleMessage(Message msg) {
            DeviceScanManager manager = weakReference.get();
            if (manager == null)
                return;
            switch (msg.what) {
                case Constant.MSG.SCAN_ONE:
                    if (manager.mScanResult != null) {
                        manager.mScanResult.deviceScanResult((DeviceInfo) msg.obj);
                    }
                    break;
                case Constant.MSG.SCAN_OVER:
                    break;
            }
        }
    }
}
