package com.bilirecommendui;

import android.app.Application;
import android.content.Context;

/**
 * by y on 2016/11/1
 */

public class App extends Application {

    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
