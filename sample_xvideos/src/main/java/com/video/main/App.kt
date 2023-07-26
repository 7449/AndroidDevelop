package com.video.main

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.video.main.net.Net
import io.reactivex.network.RxNetWork
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        val instance: App
            get() = context as App
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        RxNetWork.initOption {
            superBaseUrl { Net.XVS_BASE }
            superConverterFactory { GsonConverterFactory.create() }
        }
    }
}
