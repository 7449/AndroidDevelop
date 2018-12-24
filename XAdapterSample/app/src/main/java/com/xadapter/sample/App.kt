package com.xadapter.sample

import android.app.Application
import io.reactivex.network.RxNetWork
import io.reactivex.network.SimpleRxNetOptionFactory

/**
 * @author y
 * @create 2018/12/24
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        RxNetWork.initialization(object : SimpleRxNetOptionFactory(Api.BASE_URL, null, null) {})
    }
}
