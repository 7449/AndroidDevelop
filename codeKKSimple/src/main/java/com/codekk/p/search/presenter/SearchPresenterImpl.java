package com.codekk.p.search.presenter;

import android.text.TextUtils;

import com.codekk.p.search.model.SearchModel;
import com.codekk.p.search.view.SearchView;

import framework.base.BasePresenterImpl;
import framework.network.NetWorkRequest;
import framework.network.NetWorkSubscriber;

/**
 * by y on 2016/8/30.
 */
public class SearchPresenterImpl extends BasePresenterImpl<SearchView> implements SearchPresenter {

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
        view.showProgress();
        NetWorkRequest.getSearch(text, new NetWorkSubscriber<SearchModel>() {
            @Override
            public void onNext(SearchModel searchModel) {
                super.onNext(searchModel);
                if (page == 1) {
                    view.adapterRemove();
                }
                if (!searchModel.getProjectArray().isEmpty()) {
                    view.setData(searchModel.getProjectArray());
                } else {
                    view.noMore();
                }
                view.hideProgress();
            }
        });
    }

    @Override
    protected void netWorkError() {
        view.showExplanation();
        view.netWorkError();
        view.hideProgress();
    }
}
