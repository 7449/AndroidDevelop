package com.codekk.p.search.widget;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codekk.p.R;
import com.codekk.p.search.model.SearchModel;
import com.codekk.p.search.presenter.SearchPresenter;
import com.codekk.p.search.presenter.SearchPresenterImpl;
import com.codekk.p.search.view.SearchView;

import java.util.LinkedList;
import java.util.List;

import framework.base.BaseFragment;
import framework.data.Constant;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/30.
 */
public class SearchFragment extends BaseFragment
        implements View.OnClickListener, SearchDialog.SearchInterface, SearchView {

    private SearchPresenter mPresenter;
    private SearchAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mExplanation;
    private ProgressBar mProgress;


    @Override
    protected void initById() {
        mRecyclerView = getView(R.id.recyclerView);
        mExplanation = getView(R.id.search_explanation);
        mProgress = getView(R.id.progressBar);
        FloatingActionButton mFAB = (FloatingActionButton) getActivity().findViewById(R.id.fa_btn);
        mFAB.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        showExplanation();
        mPresenter = new SearchPresenterImpl(this);
        List<SearchModel.ProjectArrayBean> list = new LinkedList<>();
        mAdapter = new SearchAdapter(list);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LISTVIEW, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void toolbarOnclick() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fa_btn:
                SearchDialog.startSearch(getActivity(), this);
                break;
        }
    }

    @Override
    public void startSearch(String search) {
        mPresenter.netWorkRequest(search, 1);
    }

    @Override
    public void netWorkError() {
        UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.search_error));
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<SearchModel.ProjectArrayBean> projectArray) {
        mAdapter.addAll(projectArray);
    }

    @Override
    public void adapterRemove() {
        mAdapter.removeAll();
    }

    @Override
    public void searchIsEmpty() {
        UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.search_null));
    }

    @Override
    public void showExplanation() {
        mExplanation.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideExplanation() {
        mExplanation.setVisibility(View.GONE);
    }

    @Override
    public void noMore() {
        UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.recyclerview_data_null));
    }

}
