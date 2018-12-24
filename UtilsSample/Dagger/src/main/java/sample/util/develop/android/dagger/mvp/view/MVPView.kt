package sample.util.develop.android.dagger.mvp.view


import sample.util.develop.android.dagger.mvp.model.MVPBean

/**
 * by y on 2017/5/31.
 */

interface MVPView {
    fun showProgress()

    fun hideProgress()

    fun onNetError()

    fun onNetSuccess(list: List<MVPBean>)
}
