package com.xadapter.sample;

import io.reactivex.network.RxNetWork;

/**
 * by y.
 * <p>
 * Description:
 */
public class MainPresenterImpl implements MainPresenter {

    private final MainView mainView;

    MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onNetRequest(int page, int type) {
        RxNetWork
                .getInstance()
                .getApi(getClass().getSimpleName(),
                        RxNetWork.observable(Api.ZLService.class)
                                .getList("daily", 20, 0), new SimpleListNetListener(mainView, type));
    }


}
