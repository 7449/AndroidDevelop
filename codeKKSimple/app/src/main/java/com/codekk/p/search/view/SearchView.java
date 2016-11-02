package com.codekk.p.search.view;

import com.codekk.p.search.model.SearchModel;

import java.util.List;

import framework.base.BaseView;

/**
 * by y on 2016/8/30.
 */
public interface SearchView extends BaseView {

    void setData(List<SearchModel.ProjectArrayBean> projectArray);

    void adapterRemove();

    void searchIsEmpty();

    void showExplanation();

    void hideExplanation();

    void noMore();
}
