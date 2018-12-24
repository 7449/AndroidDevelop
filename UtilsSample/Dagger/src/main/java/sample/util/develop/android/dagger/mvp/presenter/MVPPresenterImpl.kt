package sample.util.develop.android.dagger.mvp.presenter


import io.reactivex.network.RxNetWork
import io.reactivex.network.RxNetWorkListener
import sample.util.develop.android.dagger.mvp.MVPApi
import sample.util.develop.android.dagger.mvp.model.MVPBean
import sample.util.develop.android.dagger.mvp.view.MVPView

import javax.inject.Inject

/**
 * by y on 2017/5/31.
 */
class MVPPresenterImpl @Inject
constructor(private val mvpView: MVPView) : MVPPresenter {

    override fun startNetWork() {
        RxNetWork.instance.cancelDefaultKey()
        RxNetWork
            .instance
            .getApi(RxNetWork.observable(MVPApi.ZLService::class.java).getList("daily", 20, 0),
                object : RxNetWorkListener<List<MVPBean>> {
                    override fun onNetWorkStart() {
                        mvpView.showProgress()
                    }

                    override fun onNetWorkError(e: Throwable) {
                        mvpView.onNetError()
                        mvpView.hideProgress()
                    }

                    override fun onNetWorkComplete() {
                        mvpView.hideProgress()
                    }

                    override fun onNetWorkSuccess(data: List<MVPBean>) {
                        mvpView.onNetSuccess(data)
                    }
                })
    }
}
