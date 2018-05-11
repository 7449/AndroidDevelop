package com.greendao.multitable;

import android.app.Application;
import android.content.Context;

/**
 * by y on 05/07/2017.
 */

public class App extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
