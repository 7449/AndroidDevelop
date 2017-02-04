package com.bilirecommendui.network;


import com.bilirecommendui.main.model.RecommendBannerModel;
import com.bilirecommendui.main.model.RecommendModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


/**
 * by y on 2016/9/18
 */
public interface RecommendService {
    String RECOMMEND_URL = "http://app.bilibili.com/";
    //推荐
    String RECOMMEND_OLD = "x/show/old";
    //推荐banner
    String RECOMMEND_BANNER = "x/banner";

    @GET(RECOMMEND_OLD)
    Observable<RecommendModel> getRecommend(@Query("screen") String screen);

    @GET(RECOMMEND_BANNER)
    Observable<RecommendBannerModel> getRecommendBanner(@Query("plat") int plat);
}