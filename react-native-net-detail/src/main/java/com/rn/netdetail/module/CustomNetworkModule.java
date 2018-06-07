package com.rn.netdetail.module;

import com.facebook.react.modules.network.OkHttpClientFactory;
import com.facebook.react.modules.network.ReactCookieJarContainer;
import com.rn.netdetail.net.LogInterceptor;

import okhttp3.OkHttpClient;

public class CustomNetworkModule implements OkHttpClientFactory {

    public OkHttpClient createNewNetworkModuleClient() {
        return new OkHttpClient.Builder()
                .cookieJar(new ReactCookieJarContainer())
//                .addInterceptor(new LogInterceptor())
                .build();
    }
}