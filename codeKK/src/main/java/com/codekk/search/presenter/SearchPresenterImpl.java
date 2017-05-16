package com.codekk.search.presenter;

import android.text.TextUtils;

import com.codekk.search.model.SearchModel;
import com.codekk.search.view.SearchView;
import com.rxnetwork.manager.RxNetWork;

import framework.base.BasePresenterImpl;
import framework.network.Api;
import framework.network.NetWorkFunc;

/**
 * by y on 2016/8/30.
 */
public class SearchPresenterImpl extends BasePresenterImpl<SearchView, SearchModel> implements SearchPresenter {

    public SearchPresenterImpl(SearchView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String text, final int page) {
        view.hideExplanation();
        if (TextUtils.isEmpty(text)) {
            view.showExplanation();
            view.searchIsEmpty();
            return;
        }

        netWork(RxNetWork
                .observable(Api.CodeKKService.class)
                .getSearch(text)
                .map(new NetWorkFunc<SearchModel>()));
    }

    @Override
    protected void netWorkError() {
        view.showExplanation();
        view.netWorkError();
        view.hideProgress();
    }

    @Override
    public void onNetWorkSuccess(SearchModel data) {
        if (!data.getProjectArray().isEmpty()) {
            view.setData(data.getProjectArray());
        } else {
            view.noMore();
        }
    }
}
