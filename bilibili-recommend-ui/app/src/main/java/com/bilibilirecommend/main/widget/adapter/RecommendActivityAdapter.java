package com.bilibilirecommend.main.widget.adapter;



import com.bilibilirecommend.R;
import com.bilibilirecommend.base.BaseListRecyclerAdapter;
import com.bilibilirecommend.base.SuperViewHolder;
import com.bilibilirecommend.main.model.RecommendModel;
import com.bilibilirecommend.utils.ImageLoaderUtils;

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
