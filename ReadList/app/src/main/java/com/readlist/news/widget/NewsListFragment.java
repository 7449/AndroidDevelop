package com.readlist.news.widget;

import android.os.Bundle;
import android.view.View;

import com.readlist.R;
import com.readlist.news.model.NewsListModel;
import com.readlist.news.presenter.NewsPresenterImpl;
import com.readlist.news.view.NewsView;

import java.util.ArrayList;
import java.util.List;

import framework.base.BaseRecyclerViewAdapter;
import framework.base.RefreshFragment;
import framework.utils.UIUtils;

/**
 * by y on 2016/11/9
 */

public class NewsListFragment extends RefreshFragment<NewsListModel, NewsListAdapter, NewsPresenterImpl>
        implements NewsView,
        BaseRecyclerViewAdapter.OnItemClickListener<NewsListModel.NewslistBean> {

    public static NewsListFragment newInstance(String suffix) {
        NewsListFragment listFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_TYPE, suffix);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Override
    protected void initCreate() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected NewsListAdapter initAdapter() {
        return new NewsListAdapter(new ArrayList<NewsListModel.NewslistBean>());
    }

    @Override
    protected NewsPresenterImpl initPresenter() {
        return new NewsPresenterImpl(this);
    }

    @Override
    protected int initRecyclerManagerCount() {
        return 1;
    }

    @Override
    protected void netWork() {
        mPresenter.netWorkRequest(suffix, page);
    }

    @Override
    protected void initBundle(Bundle bundle) {
        suffix = bundle.getString(FRAGMENT_TYPE);
    }


    @Override
    public void setData(List<NewsListModel.NewslistBean> newslist) {
        mAdapter.addAll(newslist);
    }

    @Override
    public void onItemClick(View view, int position, NewsListModel.NewslistBean info) {
        UIUtils.startBrowser(getActivity(), info.getUrl());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recyclerview;
    }

}
