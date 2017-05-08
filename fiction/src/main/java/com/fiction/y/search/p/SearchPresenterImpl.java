package com.fiction.y.search.p;

import android.text.TextUtils;

import com.fiction.y.search.m.SearchModel;
import com.fiction.y.search.v.SearchView;

import java.util.List;

import framework.base.BasePresenterImpl;
import framework.db.SearchDb;
import framework.network.Api;
import framework.network.JsoupTool;
import rx.Subscriber;

/**
 * by y on 2017/1/8.
 */

public class SearchPresenterImpl extends BasePresenterImpl<SearchView, List<SearchModel>> implements SearchPresenter {

    public SearchPresenterImpl(SearchView view) {
        super(view);
    }

    @Override
    protected void netWorkNext(List<SearchModel> searchModels) {
        view.netWorkSuccess(searchModels);
    }


    @Override
    public void startSearch(final String fictionName, final int page) {
        isProgress = page == 0;
        if (!TextUtils.isEmpty(fictionName)) {
            startNetWork(new NetWorkOnSubscribe() {
                @Override
                public void call(Subscriber<? super List<SearchModel>> subscriber) {
                    List<SearchModel> list = JsoupTool.getInstance().getList(Api.BASE_81 + fictionName + "&p=" + page + Api.SUFFIX_81);
                    if (list.isEmpty()) {
                        view.netWorkError();
                    } else {
                        SearchDb.insert(fictionName);
                        subscriber.onNext(list);
                    }
                    subscriber.onCompleted();

                }
            });
        } else {
            view.fictionNameEmpty();
        }
    }
}
