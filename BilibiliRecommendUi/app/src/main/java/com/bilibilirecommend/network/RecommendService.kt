package com.bilibilirecommend.network


import com.bilibilirecommend.main.model.RecommendBannerModel
import com.bilibilirecommend.main.model.RecommendModel

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


interface RecommendService {

    @GET(RECOMMEND_OLD)
    fun getRecommend(@Query("screen") screen: String): Observable<RecommendModel>

    @GET(RECOMMEND_BANNER)
    fun getRecommendBanner(@Query("plat") plat: Int): Observable<RecommendBannerModel>

    companion object {
        const val RECOMMEND_URL = "http://app.bilibili.com/"
        //推荐
        const val RECOMMEND_OLD = "x/show/old"
        //推荐banner
        const val RECOMMEND_BANNER = "x/banner"
    }
}