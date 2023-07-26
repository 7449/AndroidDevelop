package com.video.main.list

import android.common.ViewStatus
import android.common.core.BasePresenter
import android.common.core.BasePresenterImpl
import android.common.core.RefreshView
import com.video.main.net.Net
import com.video.main.net.VideoListEntity
import com.video.main.net.VideoUiType
import com.video.main.net.cancelRequest
import com.video.main.net.videoListApi
import io.reactivex.network.JsoupService
import io.reactivex.network.RxNetWork

interface ListPresenter : BasePresenter {
    fun onNetWork(page: Int, uiType: Int, viewStatus: ViewStatus, suffix: String)
}

class ListPresenterImpl(uiView: RefreshView<VideoListEntity>) :
    BasePresenterImpl<RefreshView<VideoListEntity>>(uiView), ListPresenter {

    private var tag = ""

    override fun onNetWork(page: Int, uiType: Int, viewStatus: ViewStatus, suffix: String) {
        val url = when (uiType) {
            VideoUiType.LANG -> {
                if (page == 0) String.format(
                    Net.XVS_LANG,
                    suffix
                ) else String.format(Net.XVS_LANG_PAGE, suffix, page)
            }

            VideoUiType.TAGS -> {
                if (page == 0) String.format(
                    Net.XVS_SEARCH,
                    suffix
                ) else String.format(Net.XVS_SEARCH_PAGE, suffix, page)
            }

            else -> ""
        }
        cancelRequest(tag)
        tag = suffix
        RxNetWork
            .observable(JsoupService::class.java)
            .get(url)
            .videoListApi(tag, mView, viewStatus) { it.listEntity.isEmpty() }
    }

    override fun onDestroy() {
        if (tag.isNotEmpty()) {
            cancelRequest(tag)
        }
        super.onDestroy()
    }

}