package com.readlist.news.presenter;

import com.readlist.news.model.NewsListModel;
import com.readlist.news.view.NewsView;

import framework.base.BasePresenterImpl;
import framework.network.NetWork;

/**
 * by y on 2016/11/9
 */

public class NewsPresenterImpl extends BasePresenterImpl<NewsView, NewsListModel> implements NewsPresenter {

    public NewsPresenterImpl(NewsView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String suffix, int page) {
        isClearAdapter = page == 1;
        startNetWork(observable = NetWork.getApiService().getNewsList(suffix, 10, page));
    }


    @Override
    protected void netWorkNext(NewsListModel newsModel) {
        switch (newsModel.getCode()) {
            case 200:
                view.setData(newsModel.getNewslist());
                break;
            default:
                view.errorMessage(newsModel.getMsg());
                break;
        }
    }

}
