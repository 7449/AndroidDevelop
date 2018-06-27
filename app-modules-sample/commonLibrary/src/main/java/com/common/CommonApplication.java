package com.common;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * by y.
 * <p>
 * Description:Common Application
 */

public class CommonApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Application getInstance() {
        return (Application) context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
