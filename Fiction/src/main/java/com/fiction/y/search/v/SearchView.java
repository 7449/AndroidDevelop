package com.fiction.y.search.v;

import com.fiction.y.search.m.SearchModel;

import java.util.List;

import framework.base.BaseView;

/**
 * by y on 2017/1/8.
 */

public interface SearchView extends BaseView<List<SearchModel>> {
    void fictionNameEmpty();
}
