package com.bilibilirecommend.main.widget.holder

import android.text.TextUtils
import android.view.View

import com.bilibilirecommend.App
import com.bilibilirecommend.R
import com.bilibilirecommend.main.model.RecommendModel
import com.bilibilirecommend.network.Constant


object RecommendFooterViewFactory {

    fun createView(type: RecommendModel.ResultBean): View {
        return when (type.type) {
            Constant.TYPE_RECOMMEND -> getInflate(R.layout.item_recommend_footer_recommend)
            Constant.TYPE_BANGUMI -> getInflate(R.layout.item_recommend_footer_fanju)
            Constant.TYPE_REGION -> {
                if (TextUtils.equals(type.head.title, "游戏区")) {
                    getInflate(R.layout.item_recommend_footer_games)
                } else getInflate(R.layout.item_recommend_footer_refresh_more)
            }
            else -> getInflate(R.layout.item_recommend_footer_refresh_more)
        }
    }


    private fun getInflate(id: Int): View {
        return View.inflate(App.instance, id, null)
    }
}
