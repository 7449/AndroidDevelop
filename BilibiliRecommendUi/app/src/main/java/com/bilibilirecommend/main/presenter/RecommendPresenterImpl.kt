package com.bilibilirecommend.main.presenter


import com.bilibilirecommend.main.model.RecommendBannerModel
import com.bilibilirecommend.main.model.RecommendModel
import com.bilibilirecommend.main.view.RecommendView
import com.bilibilirecommend.network.NetWorkRequest

import rx.Subscriber

class RecommendPresenterImpl(private val view: RecommendView) : RecommendPresenter {


    override fun netWorkRequest(plat: Int) {
        view.showProgress()
        NetWorkRequest.getRecommend(plat, object : Subscriber<Any>() {
            override fun onStart() {
                super.onStart()
                view.showProgress()
            }

            override fun onCompleted() {
                view.hideProgress()
            }

            override fun onError(e: Throwable) {
                view.hideProgress()
                view.netWorkError()
            }

            override fun onNext(o: Any) {
                if (o is RecommendBannerModel) {
                    view.setBannerData(o.data)
                }
                if (o is RecommendModel) {
                    view.removeAdapter()
                    view.setRecommendData(o)
                }
            }
        })
    }
}
