package com.fuckapp.fragment.presenter

import com.fuckapp.fragment.model.AppModel
import com.fuckapp.fragment.view.AppView
import com.fuckapp.utils.QueryAppUtils

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * by y on 2016/10/31
 */

class AppPresenterImpl(private val appView: AppView) : AppPresenter {
    private lateinit var subscription: Subscription

    fun unsubscribe() {
        if (!subscription.isUnsubscribed) {
            subscription.unsubscribe()
        }
    }

    override fun startApp(type: Int) {
        if (::subscription.isInitialized) {
            unsubscribe()
        }
        subscription = Observable.create { sub ->
            sub.onNext(QueryAppUtils.getAppInfo(type))
            sub.onCompleted()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(AppInfoSubscriber())
    }

    private inner class AppInfoSubscriber : Subscriber<List<AppModel>>() {

        override fun onStart() {
            super.onStart()
            appView.showProgress()
        }

        override fun onCompleted() {
            unsubscribe()
            appView.hideProgress()
            appView.obtainSuccess()
        }

        override fun onError(e: Throwable) {
            unsubscribe()
            appView.hideProgress()
            appView.obtainError()
        }

        override fun onNext(appModels: List<AppModel>) {
            appView.removeAllAdapter()
            appView.setAppInfo(appModels)
        }

    }
}
