package com.wifi.core.devicescan;


import android.util.Log;

import com.wifi.DeviceInfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DeviceScanGroup implements Runnable {
    private static final String tag = DeviceScanGroup.class.getSimpleName();
    Thread mThread;
    private DeviceScanHandler mDeviceScanHandler;
    private int mGroupIndex;
    private List<DeviceInfo> mIpMacList = new ArrayList<>();
    private DeviceScanTask[] mDeviceScanTaskArray = null;

    DeviceScanGroup(DeviceScanHandler handler, int groupIndex) {
        this.mDeviceScanHandler = handler;
        this.mGroupIndex = groupIndex;

    }

    void stop() {
        if (mIpMacList != null) {
            synchronized (this) {
                if (mDeviceScanTaskArray != null) {
                    for (DeviceScanTask aMDeviceScanTaskArray : mDeviceScanTaskArray) {
                        if (aMDeviceScanTaskArray != null) {
                            aMDeviceScanTaskArray.mThread.interrupt();
                            aMDeviceScanTaskArray.mThread = null;
                        }
                    }
                    mDeviceScanTaskArray = null;
                }
            }
        }
    }

    @Override
    public void run() {
        getIpMacFromFile();
        Log.d(tag, "device scan group: " + mGroupIndex + " find " + mIpMacList.size()
                + " IP_MAC");

        if (mIpMacList.size() == 0 || Thread.interrupted())
            return;

        furtherScan();
    }

    private void furtherScan() {
        DeviceScanTask task;
        DeviceInfo deviceInfo;
        int taskNum = mIpMacList.size();
        Log.d(tag, "task num = " + taskNum);

        mDeviceScanTaskArray = new DeviceScanTask[taskNum];
        for (int i = 0; i < taskNum; i++) {
            task = new DeviceScanTask();
            if (mDeviceScanTaskArray != null) {
                deviceInfo = mIpMacList.get(i);
                task.mThread = new Thread(task.mRunnable);
                task.setParams(deviceInfo, mDeviceScanHandler);
                task.mThread.setPriority(Thread.MAX_PRIORITY);
                task.mThread.start();
                mDeviceScanTaskArray[i] = task;
            }
        }
    }

    /**
     * 从proc/net/arp中读取ip_mac对
     */
    private void getIpMacFromFile() {
        String line;
        String ip;
        String mac;

        String regExp = "((([0-9,A-F,a-f]{1,2}" + ":" + "){1,5})[0-9,A-F,a-f]{1,2})";
        Pattern pattern;
        Matcher matcher;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    "/proc/net/arp"));
            bufferedReader.readLine(); //忽略标题行
            while ((line = bufferedReader.readLine()) != null) {
                ip = line.substring(0, line.indexOf(" "));
                pattern = Pattern.compile(regExp);
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    mac = matcher.group(1);
                    if (!mac.equals("00:00:00:00:00:00")) {
                        DeviceInfo ip_mac = new DeviceInfo(ip, mac.toUpperCase(Locale.US));
                        Log.d(tag, "ip_mac = " + ip_mac.toString());
                        int index = mDeviceScanHandler.mIpMacInLan.indexOf(ip_mac);
                        if (index == -1) {
                            mIpMacList.add(ip_mac);
                            mDeviceScanHandler.mIpMacInLan.add(ip_mac);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
