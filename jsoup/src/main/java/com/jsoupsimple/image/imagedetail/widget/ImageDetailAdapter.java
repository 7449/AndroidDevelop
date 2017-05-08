package com.jsoupsimple.image.imagedetail.widget;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import framework.base.BaseModel;
import framework.base.BasePagerAdapter;

import static framework.utils.ImageLoaderUtils.display;

/**
 * by y on 2016/9/26.
 */

public class ImageDetailAdapter extends BasePagerAdapter<BaseModel> {


    public ImageDetailAdapter(List<BaseModel> datas) {
        super(datas);
    }

    @Override
    protected Object instantiate(ViewGroup container, int position, BaseModel data) {
        ImageView imageView = new ImageView(container.getContext());
        display(imageView, data.getUrl());
        container.addView(imageView);
        return imageView;
    }
}
