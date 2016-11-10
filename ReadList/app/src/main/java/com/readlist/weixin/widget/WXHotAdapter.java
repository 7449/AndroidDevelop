package com.readlist.weixin.widget;

import android.text.Html;

import com.readlist.R;
import com.readlist.weixin.model.WXHotModel;

import java.util.List;

import framework.base.BaseRecyclerViewAdapter;
import framework.utils.ImageLoaderUtils;

/**
 * by y on 2016/11/8
 */

public class WXHotAdapter extends BaseRecyclerViewAdapter<WXHotModel.NewslistBean> {


    public WXHotAdapter(List<WXHotModel.NewslistBean> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_wx_hot;
    }

    @Override
    protected void onBind(BaseViewHolder holder, int position, WXHotModel.NewslistBean data) {
        ImageLoaderUtils.display(holder.getImageView(R.id.list_image), data.getPicUrl());
        holder.setTextView(R.id.list_tv, Html.fromHtml(data.getTitle()));
    }


}
