package com.bilirecommendui.main.presenter;


import com.bilirecommendui.main.model.RecommendBannerModel;
import com.bilirecommendui.main.model.RecommendModel;
import com.bilirecommendui.main.view.RecommendView;
import com.bilirecommendui.network.NetWorkRequest;

import rx.Subscriber;

/**
 * by y on 2016/9/17.
 */
public class RecommendPresenterImpl implements RecommendPresenter {

    private RecommendView view;

    public RecommendPresenterImpl(RecommendView view) {
        this.view = view;
    }


    @Override
    public void netWorkRequest(int plat) {
        view.showProgress();
        NetWorkRequest.getRecommend(plat, new Subscriber<Object>() {
            @Override
            public void onStart() {
                super.onStart();
                view.showProgress();
            }

            @Override
            public void onCompleted() {
                view.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                view.hideProgress();
                view.netWorkError();
            }

            @Override
            public void onNext(Object o) {
                if (o instanceof RecommendBannerModel) {
                    RecommendBannerModel bannerModel = (RecommendBannerModel) o;
                    view.setBannerData(bannerModel.getData());
                }
                if (o instanceof RecommendModel) {
                    RecommendModel recommendModel = (RecommendModel) o;
                    view.removeAdapter();
                    view.setRecommendData(recommendModel);
                }
            }
        });
    }
}
