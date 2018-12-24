package com.bilibilirecommend.main.widget.holder

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView

import com.bilibilirecommend.R
import com.bilibilirecommend.base.SuperViewHolder
import com.bilibilirecommend.main.model.RecommendModel
import com.bilibilirecommend.network.Constant
import com.bilibilirecommend.utils.CompoundDrawableUtils
import com.bilibilirecommend.utils.ImageLoaderUtils
import com.bilibilirecommend.utils.SpannableUtils

class RecommendItemViewHolder(superViewHolder: SuperViewHolder) {
    private val mTitle: TextView = superViewHolder[R.id.tv_title]
    private val mTitlePage: ImageView = superViewHolder[R.id.iv_title_page]
    private val mLiveName: TextView = superViewHolder[R.id.tv_live_name]
    private val mLiveWatchNum: TextView = superViewHolder[R.id.tv_live_watch_num]

    @SuppressLint("SetTextI18n")
    fun setData(bodyBean: RecommendModel.ResultBean.BodyBean, type: String) {
        when (type) {
            Constant.TYPE_LIVE -> {
                CompoundDrawableUtils.setItem(mLiveWatchNum, R.drawable.ic_watching)
                mTitle.text = SpannableUtils.getHomeTitlePageType(bodyBean.area, bodyBean.title)
                mLiveName.text = bodyBean.up
                mLiveWatchNum.text = bodyBean.online.toString()
            }
            Constant.TYPE_BANGUMI -> {
                mLiveWatchNum.setCompoundDrawables(null, null, null, null)
                mTitle.text = bodyBean.title
                mLiveName.text = bodyBean.desc1
                mLiveWatchNum.text = bodyBean.status.toString()
            }
            else -> {
                mLiveWatchNum.setCompoundDrawables(null, null, null, null)
                mLiveName.text = bodyBean.play
                mLiveWatchNum.text = "弹幕数：" + bodyBean.danmaku
                mTitle.text = bodyBean.title
            }
        }
        ImageLoaderUtils.display(mTitlePage, bodyBean.cover)
    }
}
