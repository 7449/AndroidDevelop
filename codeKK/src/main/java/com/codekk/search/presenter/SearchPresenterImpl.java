package com.codekk.search.presenter;

import android.text.TextUtils;

import com.codekk.search.model.SearchModel;
import com.codekk.search.view.SearchView;

import framework.base.BasePresenterImpl;
import framework.network.NetWork;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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


        NetWork
                .getCodeKK()
                .getSearch(text)
                .map(new NetWork.NetWorkResultFunc<SearchModel>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        netWorkError();
                    }

                    @Override
                    public void onNext(SearchModel searchModel) {
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
