package com.fuckapp.main

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

import com.fuckapp.utils.SPUtils

/**
 * by y on 2016/10/31
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SPUtils.init(applicationContext)
        context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        val instance: App
            get() = (context as App?)!!
    }
}
