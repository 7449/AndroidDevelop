package com.readlist.news.view;

import com.readlist.news.model.NewsListModel;

import java.util.List;

import framework.base.BaseView;

/**
 * by y on 2016/11/9
 */

public interface NewsView extends BaseView<NewsListModel> {
    void setData(List<NewsListModel.NewslistBean> newslist);
}
