package com.bilirecommendui.main.view;


import com.bannerlayout.model.BannerModel;
import com.bilirecommendui.main.model.RecommendModel;

import java.util.List;

/**
 * by y on 2016/9/17.
 */
public interface RecommendView {

    void setBannerData(List<BannerModel> bannerData);

    void setRecommendData(RecommendModel recommendData);

    void removeAdapter();

    void netWorkError();

    void showProgress();

    void hideProgress();
}
