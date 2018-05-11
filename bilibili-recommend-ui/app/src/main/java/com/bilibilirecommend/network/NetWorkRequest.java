package com.bilibilirecommend.network;



import com.bilibilirecommend.main.model.RecommendBannerModel;
import com.bilibilirecommend.main.model.RecommendModel;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * by y on 2016/9/13
 */
public class NetWorkRequest {


    public static void getRecommend(int plat, Subscriber<Object> subscriber) {
        Observable<RecommendModel> recommend = NetWork.getApi().getRecommend("xxhdpi");
        Observable<RecommendBannerModel> recommendBanner = NetWork.getApi()
                .getRecommendBanner(plat);
        Observable.merge(recommendBanner, recommend)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
