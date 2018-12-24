package com.bilibilirecommend.network


import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

object NetWorkRequest {


    fun getRecommend(plat: Int, subscriber: Subscriber<Any>) {
        val recommend = NetWork.api.getRecommend("xxhdpi")
        val recommendBanner = NetWork.api
                .getRecommendBanner(plat)
        Observable.merge(recommendBanner, recommend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)
    }


}
