package com.fiction.y.contents.p;

import com.fiction.y.contents.m.ContentsModel;
import com.fiction.y.contents.v.ContentsView;

import java.util.List;

import framework.base.BasePresenterImpl;
import framework.network.JsoupTool;
import rx.Subscriber;

/**
 * by y on 2017/1/8.
 */

public class ContentsPresenterImpl extends BasePresenterImpl<ContentsView, List<ContentsModel>> implements ContentsPresenter {

    public ContentsPresenterImpl(ContentsView view) {
        super(view);
    }

    @Override
    protected void netWorkNext(List<ContentsModel> contentsModels) {
        view.netWorkSuccess(contentsModels);
    }

    @Override
    public void startContents(final String url) {
        startNetWork(new NetWorkOnSubscribe() {
            @Override
            public void call(Subscriber<? super List<ContentsModel>> subscriber) {
                List<ContentsModel> list = JsoupTool.getInstance().getContents(url);
                subscriber.onNext(list);
                subscriber.onCompleted();
            }

        });
    }
}
