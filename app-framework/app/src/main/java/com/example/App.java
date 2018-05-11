package com.example;

import com.common.CommonApplication;
import com.squareup.leakcanary.LeakCanary;

import io.reactivex.network.RxNetWork;

/**
 * by y.
 * <p>
 * Description:Sample Application
 */

public class App extends CommonApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (com.common.BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
        RxNetWork
                .getInstance()
                .setBaseUrl(Api.ZL_BASE_API);
    }
}
