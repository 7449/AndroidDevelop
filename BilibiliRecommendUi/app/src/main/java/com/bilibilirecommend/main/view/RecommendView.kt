package com.bilibilirecommend.main.view


import com.bannerlayout.model.BannerModel
import com.bilibilirecommend.main.model.RecommendModel

interface RecommendView {

    fun setBannerData(bannerData: List<BannerModel>)

    fun setRecommendData(recommendData: RecommendModel)

    fun removeAdapter()

    fun netWorkError()

    fun showProgress()

    fun hideProgress()
}
