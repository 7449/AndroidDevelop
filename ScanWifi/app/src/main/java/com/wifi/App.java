package com.wifi;


import android.app.Application;


public class App extends Application {
    private static App context;

    public static App getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
