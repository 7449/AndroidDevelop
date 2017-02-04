package com.bilirecommendui.main.widget.holder;


import android.view.View;

import com.bannerlayout.model.BannerModel;
import com.bannerlayout.widget.BannerLayout;
import com.bilirecommendui.R;
import com.bilirecommendui.base.SuperViewHolder;

import java.util.List;

/**
 * by y on 2016/9/17.
 */
public class RecommendBannerHolder extends SuperViewHolder {

    private BannerLayout bannerLayout;

    public RecommendBannerHolder(View itemView) {
        super(itemView);
        bannerLayout = get(R.id.banner);
    }

    public void setBanner(List<BannerModel> banner) {
        bannerLayout.initImageListResources(banner).initAdapter().initRound().start(true);
    }

}
