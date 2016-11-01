package com.bilirecommendui.main.widget.holder;


import android.view.View;
import android.widget.ImageView;

import com.bilirecommendui.R;
import com.bilirecommendui.base.SuperViewHolder;
import com.bilirecommendui.main.model.RecommendModel;
import com.bilirecommendui.network.ImageLoaderUtils;

import java.util.List;

/**
 * by y on 2016/9/18.
 */
public class RecommendTopicHolder extends SuperViewHolder {

    private ImageView imageView;

    public RecommendTopicHolder(View itemView) {
        super(itemView);
        imageView = get(R.id.iv_topic);
    }

    public void setTopicData(List<RecommendModel.ResultBean.BodyBean> body) {
        ImageLoaderUtils.display(imageView, body.get(0).getCover());
    }
}
