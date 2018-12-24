package com.xadapter.sample

import android.util.Log
import com.status.layout.EMPTY
import com.status.layout.ERROR
import com.status.layout.LOADING
import com.status.layout.SUCCESS
import io.reactivex.network.RxNetWorkListener

/**
 * by y.
 *
 *
 * Description:
 */
class SimpleListNetListener(private val mainView: MainView, private val type: Int) : RxNetWorkListener<List<Entity>> {

    private val page: Int = mainView.page

    override fun onNetWorkStart() {
        if (type == SimpleAdapter.TYPE_STATUS) {
            mainView.onChangeRootLayoutStatus(LOADING)
        }
    }

    override fun onNetWorkError(e: Throwable) {
        Log.d(javaClass.simpleName, e.message)
        if (page == 0 && type == SimpleAdapter.TYPE_STATUS) {
            mainView.onChangeRootLayoutStatus(ERROR)
        } else {
            mainView.onNetError(type)
        }
    }

    override fun onNetWorkComplete() {}

    override fun onNetWorkSuccess(data: List<Entity>) {
        if (page == 0) {
            mainView.onRemoveAll()
        }
        if (data.isEmpty()) {
            if (page == 0) {
                mainView.onChangeRootLayoutStatus(EMPTY)
            } else {
                mainView.onLoadNoMore()
            }
        } else {
            mainView.onNetSuccess(data)
            mainView.onPagePlus()
            if (page == 0 && type == SimpleAdapter.TYPE_STATUS) {
                mainView.onChangeRootLayoutStatus(SUCCESS)
            } else {
                mainView.onNetComplete(type)
            }
        }
    }
}
