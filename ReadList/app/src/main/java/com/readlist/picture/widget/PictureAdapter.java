package com.readlist.picture.widget;

import android.text.Html;

import com.readlist.R;
import com.readlist.picture.model.PictureModel;

import java.util.List;

import framework.base.BaseRecyclerViewAdapter;
import framework.utils.ImageLoaderUtils;

/**
 * by y on 2016/11/8
 */

public class PictureAdapter extends BaseRecyclerViewAdapter<PictureModel> {


    public PictureAdapter(List<PictureModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_picture;
    }

    @Override
    protected void onBind(BaseViewHolder holder, int position, PictureModel data) {
        ImageLoaderUtils.display(holder.getImageView(R.id.list_image), data.getPicUrl());
        holder.setTextView(R.id.list_tv, Html.fromHtml(data.getTitle()));
    }
}
