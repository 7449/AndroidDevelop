package com.objectbox.multitable;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * by y on 28/09/2017.
 */

public class App extends Application {


    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }


    public static Context getContext() {
        return context;
    }
}
