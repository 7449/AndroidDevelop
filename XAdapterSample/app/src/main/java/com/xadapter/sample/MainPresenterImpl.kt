package com.xadapter.sample

import io.reactivex.network.RxNetWork

/**
 * by y.
 *
 *
 * Description:
 */
class MainPresenterImpl internal constructor(private val mainView: MainView) : MainPresenter {

    override fun onNetRequest(page: Int, type: Int) {
        RxNetWork
                .instance
                .getApi(javaClass.simpleName,
                        RxNetWork.observable(Api.ZLService::class.java)
                                .getList("daily", 20, 0), SimpleListNetListener(mainView, type))
    }
}
