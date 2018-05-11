package com.dagger.mvp.presenter;

import com.dagger.mvp.MVPApi;
import com.dagger.mvp.model.MVPBean;
import com.dagger.mvp.view.MVPView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.network.manager.RxNetWork;
import io.reactivex.network.manager.RxNetWorkListener;

/**
 * by y on 2017/5/31.
 */
public class MVPPresenterImpl implements MVPPresenter {

    private final MVPView mvpView;

    @Inject
    public MVPPresenterImpl(MVPView mvpView) {
        this.mvpView = mvpView;
    }


    @Override
    public void startNetWork() {
        RxNetWork.getInstance().cancel(RxNetWork.TAG);
        RxNetWork
                .getInstance()
                .setBaseUrl(MVPApi.ZL_BASE_API)
                .getApi(RxNetWork.observable(
                        MVPApi.ZLService.class).getList("daily", 20, 0),
                        new RxNetWorkListener<List<MVPBean>>() {
                            @Override
                            public void onNetWorkStart() {
                                mvpView.showProgress();
                            }

                            @Override
                            public void onNetWorkError(Throwable e) {
                                mvpView.onNetError();
                                mvpView.hideProgress();
                            }

                            @Override
                            public void onNetWorkComplete() {
                                mvpView.hideProgress();
                            }

                            @Override
                            public void onNetWorkSuccess(List<MVPBean> data) {
                                mvpView.onNetSuccess(data);
                            }
                        });
    }
}
