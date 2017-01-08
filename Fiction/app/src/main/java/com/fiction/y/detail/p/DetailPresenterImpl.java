package com.fiction.y.detail.p;

import com.fiction.y.detail.m.DetailModel;
import com.fiction.y.detail.v.DetailView;

import framework.base.BasePresenterImpl;
import framework.network.JsoupTool;
import rx.Subscriber;

/**
 * by y on 2017/1/8.
 */

public class DetailPresenterImpl extends BasePresenterImpl<DetailView, DetailModel> implements DetailPresenter {

    public DetailPresenterImpl(DetailView view) {
        super(view);
    }

    @Override
    public void startDetail(final String url) {
        startNetWork(new NetWorkOnSubscribe() {
            @Override
            public void call(Subscriber<? super DetailModel> subscriber) {
                DetailModel detailModel = JsoupTool.getInstance().getDetail(url);
                subscriber.onNext(detailModel);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    protected void netWorkNext(DetailModel detailModel) {
        view.netWorkSuccess(detailModel);
    }
}
