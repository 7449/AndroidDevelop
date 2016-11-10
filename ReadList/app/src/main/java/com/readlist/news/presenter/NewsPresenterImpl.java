package com.readlist.news.presenter;

import com.readlist.news.model.NewsListModel;
import com.readlist.news.view.NewsView;

import framework.base.BaseModel;
import framework.base.BasePresenterImpl;
import framework.network.NetWork;

/**
 * by y on 2016/11/9
 */

public class NewsPresenterImpl extends BasePresenterImpl<NewsView, BaseModel<NewsListModel>> implements NewsPresenter {

    public NewsPresenterImpl(NewsView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String suffix, int page) {
        isClearAdapter = page == 1;
        startNetWork(observable = NetWork.getApiService().getNewsList(suffix, 10, page));
    }

    @Override
    protected void netWorkNext(BaseModel<NewsListModel> newsListModelBaseModel) {
        switch (newsListModelBaseModel.getCode()) {
            case 200:
                view.setData(newsListModelBaseModel.getNewsList());
                break;
            default:
                view.errorMessage(newsListModelBaseModel.getMsg());
                break;
        }
    }

}
