package com.jsoupsimple.image.imagelist.widget.adapter;


import com.jsoupsimple.R;

import java.util.List;

import framework.base.BaseModel;
import framework.base.BaseRecyclerAdapter;
import framework.base.SuperViewHolder;

import static framework.utils.ImageLoaderUtils.display;

/**
 * by y on 2016/9/26.
 */

public class ImageAdapter extends BaseRecyclerAdapter<BaseModel> {

    public ImageAdapter(List<BaseModel> mDatas) {
        super(mDatas);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.image_item;
    }

    @Override
    protected void onBind(SuperViewHolder viewHolder, int position, BaseModel mDatas) {
        display(viewHolder.getImageView(R.id.image), mDatas.getUrl());
    }


}
