package com.bilirecommendui.network;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * by y on 2016/9/13
 */
public class NetWork {

    private static RecommendService apiService;
    private static final Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static final CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static OkHttpClient getOkHttp() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
    }

    public static RecommendService getApi() {
        if (apiService == null) {
            apiService = getRetrofit(RecommendService.RECOMMEND_URL).create(RecommendService.class);
        }
        return apiService;
    }

    private static Retrofit getRetrofit(String url) {
        return new Retrofit.Builder()
                .client(getOkHttp())
                .baseUrl(url)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }

}
