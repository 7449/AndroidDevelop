package com.wifi;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wifi.core.devicescan.DeviceScanManager;
import com.wifi.core.devicescan.DeviceScanResult;
import com.wifi.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class DeviceScanActivity extends AppCompatActivity {
    private DeviceScanManager manager;
    private Toolbar mToolbar;
    private DeviceScanAdapter mAdapter;
    private List<DeviceInfo> mDeviceList = new ArrayList<>();


    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_scan);
        if (!NetworkUtil.isWifiConnected(this)) {
            Toast.makeText(this, R.string.connect_wifi_please, Toast.LENGTH_SHORT);
            finish();
            return;
        }
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        manager = new DeviceScanManager();
        manager.startScan(new DeviceScanResult() {
            @Override
            public void deviceScanResult(DeviceInfo deviceInfo) {
                if (!mDeviceList.contains(deviceInfo)) {
                    mDeviceList.add(deviceInfo);
                    mAdapter.notifyDataSetChanged();
                    mToolbar.setTitle("已扫描到的设备" + Integer.toString(mDeviceList.size()));
                }
            }
        });

        String localIp = NetworkUtil.getLocalIp();
        String gateIp = NetworkUtil.getGateWayIp();
        String localMac = NetworkUtil.getLocalMac();
        DeviceInfo deviceInfo = new DeviceInfo(localIp, localMac);
        deviceInfo.mManufacture = Build.MANUFACTURER;
        deviceInfo.mDeviceName = Build.MODEL;
        mDeviceList.add(deviceInfo);
        RecyclerView mRecyclerView = findViewById(R.id.device_recycle_view);
        mAdapter = new DeviceScanAdapter(mDeviceList, localIp, gateIp);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (manager != null) {
            manager.stopScan();
        }
    }


    private static class DeviceScanAdapter extends RecyclerView.Adapter<DeviceScanAdapter.DeviceHolder> {

        private List<DeviceInfo> mDeviceList;
        private String mLocalIp;
        private String mGateIp;

        DeviceScanAdapter(List<DeviceInfo> list, String localIp, String gateip) {
            this.mDeviceList = list;
            this.mLocalIp = localIp;
            this.mGateIp = gateip;
        }

        @Override
        public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DeviceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deivce, parent, false));
        }

        @Override
        public void onBindViewHolder(DeviceHolder holder, int position) {
            DeviceInfo deviceInfo = mDeviceList.get(position);
            if (deviceInfo != null) {
                holder.mDeviceIp.setText(String.format(holder.itemView.getContext().getResources().getString(R.string.ip_address), deviceInfo.mIp));
                holder.mDeviceMac.setText(String.format(holder.itemView.getContext().getResources().getString(R.string.mac_address), deviceInfo.mMac));
                if (deviceInfo.mIp.equals(mLocalIp)) {
                    holder.mDeviceName.setText(String.format(holder.itemView.getContext().getString(R.string.your_phone), deviceInfo.mDeviceName));
                } else if (deviceInfo.mIp.equals(mGateIp)) {
                    holder.mDeviceName.setText(holder.itemView.getContext().getString(R.string.gate_net));
                } else {
                    holder.mDeviceName.setText(deviceInfo.mDeviceName);
                }
                holder.mDeviceManufacture.setText(deviceInfo.mManufacture);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return mDeviceList.size();
        }

        class DeviceHolder extends RecyclerView.ViewHolder {
            TextView mDeviceIp;
            TextView mDeviceMac;
            TextView mDeviceName;
            TextView mDeviceManufacture;

            DeviceHolder(View itemView) {
                super(itemView);
                mDeviceIp = itemView.findViewById(R.id.device_ip);
                mDeviceMac = itemView.findViewById(R.id.device_mac);
                mDeviceName = itemView.findViewById(R.id.device_name);
                mDeviceManufacture = itemView.findViewById(R.id.device_manufacture);
            }
        }
    }

}
