package com.bilibilirecommend.main.widget.holder


import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.TextView

import com.bilibilirecommend.App
import com.bilibilirecommend.R
import com.bilibilirecommend.base.SuperViewHolder
import com.bilibilirecommend.main.model.RecommendModel
import com.bilibilirecommend.network.Constant
import com.bilibilirecommend.utils.CompoundDrawableUtils
import com.bilibilirecommend.utils.SpannableUtils


class RecommendHeaderHolder(superViewHolder: SuperViewHolder) {

    private val mTitle: TextView = superViewHolder[R.id.tv_title]
    private val mTv: TextView = superViewHolder[R.id.tv]

    fun setHeaderData(head: List<RecommendModel.ResultBean>, newPosition: Int) {
        val headBean = head[newPosition].head
        mTitle.text = headBean.title
        when (head[newPosition].type) {
            Constant.TYPE_RECOMMEND -> {
                setRecommendTv()
                setTitleImage(R.drawable.ic_header_hot)
            }
            Constant.TYPE_LIVE -> {
                setTitleImage(R.drawable.ic_head_live)
                setLiveTv(headBean.count)
            }
            Constant.TYPE_BANGUMI -> {
                CompoundDrawableUtils.setTitle(mTitle, getRegionImage(headBean.param))
                setBangumi()
            }
            Constant.TYPE_WEB_LINK -> {
                setTitleImage(R.drawable.ic_header_topic)
                mTitle.text = "话题"
                setDefault()
            }
            else -> {
                CompoundDrawableUtils.setTitle(mTitle, getRegionImage(headBean.param))
                setDefault()
            }
        }
    }

    private fun setTitleImage(id: Int) {
        CompoundDrawableUtils.setTitle(mTitle, id)
    }

    private fun getRegionImage(param: String): Int {
        when (param) {
            "1" -> return R.drawable.ic_category_t1
            "3" -> return R.drawable.ic_category_t3
            "129" -> return R.drawable.ic_category_t129
            "4" -> return R.drawable.ic_category_t4
            "119" -> return R.drawable.ic_category_t119
            "160" -> return R.drawable.ic_category_t160
            "36" -> return R.drawable.ic_category_t36
            "155" -> return R.drawable.ic_category_t155
            "5" -> return R.drawable.ic_category_t5
            "11" -> return R.drawable.ic_category_t11
            "23" -> return R.drawable.ic_category_t23
            "13" -> return R.drawable.ic_category_t13
            "subarea" -> return R.drawable.ic_header_activity_center
            else -> return R.drawable.ic_category_t1
        }
    }

    private fun setRecommendTv() {
        mTv.clearComposingText()
        mTv.setTextColor(ContextCompat.getColor(App.instance, R.color.white))
        mTv.setBackgroundColor(ContextCompat.getColor(App.instance, R.color.yellow))
        mTv.compoundDrawablePadding = 5
        mTv.gravity = Gravity.CENTER
        mTv.setText(R.string.home_recommend_header_ranking)
        CompoundDrawableUtils.setRecommend(mTv, R.drawable.ic_header_indicator_rank)
    }

    private fun setBangumi() {
        mTv.setBackgroundColor(ContextCompat.getColor(App.instance, R.color.gray))
        mTv.text = App.instance.getString(R.string.home_recommend_header_look_more)
    }

    private fun setDefault() {
        mTv.setCompoundDrawables(null, null, null, null)
        mTv.setTextColor(ContextCompat.getColor(App.instance, R.color.white))
        mTv.setBackgroundColor(ContextCompat.getColor(App.instance, R.color.gray))
        mTv.text = App.instance.getString(R.string.home_recommend_header_look)
    }

    private fun setLiveTv(count: Int?) {
        mTv.setBackgroundDrawable(null)
        mTv.setTextColor(ContextCompat.getColor(App.instance, R.color.black))
        mTv.compoundDrawablePadding = 8
        CompoundDrawableUtils.setLive(mTv, R.drawable.ic_gray_arrow_right)
        if (count != null)
            mTv.text = SpannableUtils.getHomeCountText(count)
    }

}