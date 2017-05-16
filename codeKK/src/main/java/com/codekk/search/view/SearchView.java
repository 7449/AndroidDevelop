package com.codekk.search.view;

import com.codekk.search.model.SearchModel;

import framework.base.BaseView;

/**
 * by y on 2016/8/30.
 */
public interface SearchView extends BaseView.BaseListView<SearchModel.ProjectArrayBean> {

    void searchIsEmpty();

    void showExplanation();

    void hideExplanation();
}
