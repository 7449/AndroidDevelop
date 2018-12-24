package com.bilibilirecommend.main.widget.adapter


import com.bilibilirecommend.R
import com.bilibilirecommend.base.BaseListRecyclerAdapter
import com.bilibilirecommend.base.SuperViewHolder
import com.bilibilirecommend.main.model.RecommendModel
import com.bilibilirecommend.utils.ImageLoaderUtils

class RecommendActivityAdapter(mDatas: MutableList<RecommendModel.ResultBean.BodyBean>) :
        BaseListRecyclerAdapter<RecommendModel.ResultBean.BodyBean>(mDatas) {

    override val layoutId: Int = R.layout.item_recommend_activity_item

    override fun onBind(viewHolder: SuperViewHolder, position: Int, mDatas: RecommendModel.ResultBean.BodyBean) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.iv_title_page), mDatas.cover)
        viewHolder.setTextView(R.id.tv_title, mDatas.title)
    }

}
