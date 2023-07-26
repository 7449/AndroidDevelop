package com.android.mvp.lifecycle.mvp

import com.android.mvp.lifecycle.base.BasePresenterImpl

/**
 * @author y
 * @create 2019/3/31
 */
class MainPresenterImpl(view: MainView) : BasePresenterImpl<MainView>(view), MainPresenter {

    override fun netWork() {
        mView?.showToast()
    }

}
