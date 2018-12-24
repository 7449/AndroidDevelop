package com.fuckapp.main;

import android.app.Application;
import android.content.Context;

import com.fuckapp.utils.SPUtils;

/**
 * by y on 2016/10/31
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        SPUtils.init(getApplicationContext());
        context = getApplicationContext();
    }

    public static Context getApp() {
        return context;
    }
}
