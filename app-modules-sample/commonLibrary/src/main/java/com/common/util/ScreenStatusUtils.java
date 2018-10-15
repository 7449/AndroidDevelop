package com.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class ScreenStatusUtils {

    public interface ScreenStatusListener {
        void onScreenOn();

        void onScreenOff();

        void userPresent();
    }

    private Context mContext;
    private IntentFilter mScreenStatusFilter;
    private ScreenStatusListener mScreenStatusListener = null;

    public ScreenStatusUtils(Context context) {
        mContext = context;
        mScreenStatusFilter = new IntentFilter();
        mScreenStatusFilter.addAction(Intent.ACTION_SCREEN_ON);
        mScreenStatusFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mScreenStatusFilter.addAction(Intent.ACTION_USER_PRESENT);
    }

    private BroadcastReceiver mScreenStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                if (mScreenStatusListener != null) {
                    mScreenStatusListener.onScreenOn();
                }
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                if (mScreenStatusListener != null) {
                    mScreenStatusListener.onScreenOff();
                }
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                if (mScreenStatusListener != null) {
                    mScreenStatusListener.userPresent();
                }
            }
        }
    };

    public void setScreenStatusListener(ScreenStatusListener l) {
        mScreenStatusListener = l;
    }


    public void startListen() {
        if (mContext != null) {
            mContext.registerReceiver(mScreenStatusReceiver, mScreenStatusFilter);
        }
    }

    public void stopListen() {
        if (mContext != null) {
            mContext.unregisterReceiver(mScreenStatusReceiver);
        }
    }
}
