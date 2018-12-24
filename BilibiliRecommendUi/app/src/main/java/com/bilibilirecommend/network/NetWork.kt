package com.bilibilirecommend.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object NetWork {

    private val gsonConverterFactory = GsonConverterFactory.create()
    private val rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create()
    private var apiService: RecommendService? = null

    private val okHttp: OkHttpClient
        get() = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(LogInterceptor())
                .build()

    val api: RecommendService
        get() {
            if (apiService == null) {
                apiService = getRetrofit(RecommendService.RECOMMEND_URL).create(RecommendService::class.java)
            }
            return apiService!!
        }

    private fun getRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
                .client(okHttp)
                .baseUrl(url)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build()
    }

    private class LogInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request()
            Log.i("LogUtils--> ", "request:" + request.toString())
            val response = chain.proceed(chain.request())
            val mediaType = response.body().contentType()
            val content = response.body().string()
            Log.i("LogUtils--> ", "response body:$content")
            return if (response.body() != null) {
                val body = ResponseBody.create(mediaType, content)
                response.newBuilder().body(body).build()
            } else {
                response
            }
        }
    }

}
