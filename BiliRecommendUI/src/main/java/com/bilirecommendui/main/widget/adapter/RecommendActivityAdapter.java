package com.bilirecommendui.main.widget.adapter;


import com.bilirecommendui.R;
import com.bilirecommendui.base.BaseListRecyclerAdapter;
import com.bilirecommendui.base.SuperViewHolder;
import com.bilirecommendui.main.model.RecommendModel;
import com.bilirecommendui.network.ImageLoaderUtils;

import java.util.List;

/**
 * by y on 2016/9/19.
 */
public class RecommendActivityAdapter extends BaseListRecyclerAdapter<RecommendModel.ResultBean.BodyBean> {


    public RecommendActivityAdapter(List<RecommendModel.ResultBean.BodyBean> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_recommend_activity_item;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, RecommendModel.ResultBean.BodyBean mDatas) {
        ImageLoaderUtils.display(viewHolder.getImageView(R.id.iv_title_page), mDatas.getCover());
        viewHolder.setTextView(R.id.tv_title, mDatas.getTitle());
    }

}
