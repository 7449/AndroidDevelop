package com.video.main.detail

import android.common.core.BasePresenter
import android.common.core.BasePresenterImpl
import android.common.core.DetailView
import com.video.main.net.Net
import com.video.main.net.VideoDetailEntity
import com.video.main.net.cancelRequest
import com.video.main.net.videoDetailApi
import io.reactivex.network.JsoupService
import io.reactivex.network.RxNetWork

interface DetailPresenter : BasePresenter {
    fun onNetWork(url: String)
}

class DetailPresenterImpl(uiView: DetailView<VideoDetailEntity>) :
    BasePresenterImpl<DetailView<VideoDetailEntity>>(uiView), DetailPresenter {

    private var tag = ""

    override fun onNetWork(url: String) {
        cancelRequest(tag)
        tag = Net.getTag(url)
        RxNetWork
            .observable(JsoupService::class.java)
            .get(url)
            .videoDetailApi(tag, mView)
    }

    override fun onDestroy() {
        if (tag.isNotEmpty()) {
            cancelRequest(tag)
        }
        super.onDestroy()
    }
}