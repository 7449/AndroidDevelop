package com.wifi.example;

import android.app.Application;

public class App extends Application {

    public static App app = null;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
