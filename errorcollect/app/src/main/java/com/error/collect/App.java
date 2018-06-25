package com.error.collect;

import android.app.Application;

import com.error.collect.library.ExceptionHandler;

/**
 * by y.
 * <p>
 * Description:
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ExceptionHandler mCustomCrashHandler = ExceptionHandler.getInstance();
        mCustomCrashHandler.setCustomCrashHandler(this);
    }
}
