package com.video.main.net

import android.common.ViewStatus
import android.common.core.DetailView
import android.common.core.RefreshView
import android.common.core.getJson
import com.android.status.layout.StatusLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.video.main.App
import io.reactivex.Observable
import io.reactivex.network.RxNetWork
import io.reactivex.network.jsoupApi
import okhttp3.ResponseBody

object Net {

    private val assetsBean by lazy {
        Gson().fromJson<ArrayList<AssetsUrlBean>>(
            App.instance.getJson(),
            object : TypeToken<ArrayList<AssetsUrlBean>>() {}.type
        )
    }

    val XVS_BASE: String = getUrl("xvs-base")
    val XVS_LANG_PAGE: String = getUrl("xvs-lang-page")
    val XVS_LANG: String = getUrl("xvs-lang")
    val XVS_LANG_TAGS: String = getUrl("xvs-lang-tag")
    val XVS_SEARCH_PAGE: String = getUrl("xvs-search-page")
    val XVS_SEARCH: String = getUrl("xvs-search")

    fun getTag(url: String) = url.hashCode().toString()

    private fun getUrl(type: String) = assetsBean.find { it.type == type }?.url ?: ""

}

fun Observable<ResponseBody>.videoDetailApi(tag: Any, view: DetailView<VideoDetailEntity>?) {
    jsoupApi(tag) {
        onNetWorkStart { view?.onChangeLayoutStatus(StatusLayout.LOADING) }
        onNetWorkError { view?.onChangeLayoutStatus(StatusLayout.ERROR) }
        onNetWorkSuccess {
            view?.onChangeLayoutStatus(StatusLayout.SUCCESS)
            view?.onNetSuccess(it)
        }
        jsoupRule { it.detailRule() }
        getBaseUrl(Net.XVS_BASE)
    }
}

fun Observable<ResponseBody>.videoListApi(
    tag: Any,
    view: RefreshView<VideoListEntity>?,
    type: ViewStatus,
    isNullOrEmpty: ((entity: VideoListEntity) -> Boolean)
) {
    jsoupApi(tag) {
        onNetWorkStart { if (type == ViewStatus.STATUS) view?.onChangeLayoutStatus(StatusLayout.LOADING) }
        onNetWorkError {
            if ((view?.page ?: 0) == 0 && type == ViewStatus.STATUS) {
                view?.onChangeLayoutStatus(StatusLayout.ERROR)
            } else {
                view?.onNetError(type)
            }
        }
        onNetWorkSuccess {
            if ((view?.page ?: 0) == 0) {
                view?.onRemoveAll()
            }
            if (isNullOrEmpty(it)) {
                if ((view?.page ?: 0) == 0) {
                    view?.onChangeLayoutStatus(StatusLayout.EMPTY)
                } else {
                    view?.onLoadNoMore()
                }
            } else {
                if ((view?.page ?: 0) == 0 && type == ViewStatus.STATUS) {
                    view?.onChangeLayoutStatus(StatusLayout.SUCCESS)
                } else {
                    view?.onNetComplete(type)
                }
                view?.onNetSuccess(it)
            }
        }
        jsoupRule { it.listRule() }
        getBaseUrl(Net.XVS_BASE)
    }
}

fun cancelRequest(tag: Any) {
    val arrayMap = RxNetWork.instance.getMap()
    arrayMap[tag]?.let {
        if (!it.isDisposed) {
            it.dispose()
        }
    }
    arrayMap.remove(tag)
}

fun cancelRequestAll() {
    val arrayMap = RxNetWork.instance.getMap()
    arrayMap.forEach {
        if (!it.value.isDisposed) {
            it.value.dispose()
        }
    }
    arrayMap.clear()
}