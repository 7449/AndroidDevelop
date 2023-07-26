package com.wifi.core.devicescan;


import android.content.Context;
import android.text.TextUtils;

import com.wifi.R;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


class Manufacture {
    private static Properties mManufacture = null;

    static Manufacture getInstance() {
        return ManufactureHolder.MANUFACTURE;
    }

    final String getManufacture(String mac, Context context) {
        String vendor;
        if (TextUtils.isEmpty(mac) || (mac.length() < 8)) return null;
        synchronized (this) {
            if (mManufacture == null) {
                mManufacture = new Properties();
                try {
                    InputStream in = context.getResources().openRawResource(R.raw.manufacture);
                    mManufacture.load(in);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            String key = mac.substring(0, 2) + mac.substring(3, 5) + mac.substring(6, 8);
            vendor = mManufacture.getProperty(key);
            if (vendor != null) {
                try {
                    vendor = new String(vendor.getBytes("ISO8859-1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return vendor;
    }

    private static final class ManufactureHolder {
        private static final Manufacture MANUFACTURE = new Manufacture();
    }
}
