package com.android.mvp.lifecycle.mvp

import com.android.mvp.lifecycle.base.BasePresenterImpl
import io.reactivex.network.RxNetWorkListener

/**
 * @author y
 * @create 2019/3/31
 */
class MainPresenterImpl(view: MainView) : BasePresenterImpl<MainView>(view), MainPresenter, RxNetWorkListener<Any> {
    override fun netWork() {
//        RxNetWork
//            .instance
//            .getApi(tag, observable.compose(bindLifecycle()), this)
        mView?.showToast()
    }

    override fun onNetWorkComplete() {}
    override fun onNetWorkError(e: Throwable) {}
    override fun onNetWorkStart() {}
    override fun onNetWorkSuccess(data: Any) {}
}
