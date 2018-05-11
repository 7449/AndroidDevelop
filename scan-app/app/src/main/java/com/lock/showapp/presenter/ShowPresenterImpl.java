package com.lock.showapp.presenter;

import com.lock.showapp.view.ShowView;

import framework.base.BasePresenterImpl;

/**
 * by y on 2017/2/16
 */

public class ShowPresenterImpl extends BasePresenterImpl<ShowView> implements ShowPresenter {

    public ShowPresenterImpl(ShowView view) {
        super(view);
    }

    @Override
    public void showLayout(boolean b) {
        if (b) {
            view.showEmptyView();
            view.hideRecyclerView();
        } else {
            view.hideEmptyView();
            view.showRecyclerView();
            view.initRecyclerView();
        }
    }
}
