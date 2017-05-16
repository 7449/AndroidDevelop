package com.codekk.search.widget;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codekk.R;
import com.codekk.search.model.SearchModel;
import com.codekk.search.presenter.SearchPresenter;
import com.codekk.search.presenter.SearchPresenterImpl;
import com.codekk.search.view.SearchView;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import java.util.List;

import butterknife.BindView;
import framework.base.BaseFragment;
import framework.data.Constant;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/30.
 */
public class SearchFragment extends BaseFragment
        implements View.OnClickListener,
        SearchDialog.SearchInterface,
        SearchView,
        OnXBindListener<SearchModel.ProjectArrayBean> {

    @BindView(R.id.progressBar)
    ProgressBar mProgress;
    @BindView(R.id.search_explanation)
    TextView mExplanation;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private SearchPresenter mPresenter;
    private XRecyclerViewAdapter<SearchModel.ProjectArrayBean> mAdapter;

    @Override
    public void onToolbarClick() {
        mRecyclerView.smoothScrollToPosition(0);
    }


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Override
    protected void initActivityCreated() {
        FloatingActionButton mFAB = (FloatingActionButton) getActivity().findViewById(R.id.fa_btn);
        mFAB.setOnClickListener(this);
        showExplanation();
        mPresenter = new SearchPresenterImpl(this);
        mAdapter = new XRecyclerViewAdapter<>();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LISTVIEW, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter
                .setLayoutId(R.layout.item_search).onXBind(this));
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
        UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.search_error));
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
        mAdapter.addAllData(projectArray);
    }

    @Override
    public void adapterRemove() {
        mAdapter.removeAll();
    }

    @Override
    public void searchIsEmpty() {
        UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.search_null));
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
        UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.recyclerview_data_null));
    }

    @Override
    public void onXBind(XViewHolder holder, int position, SearchModel.ProjectArrayBean projectArrayBean) {
        holder.setTextView(R.id.tv_author_name, TextUtils.concat("开源者：", projectArrayBean.getAuthorName()));
        holder.setTextView(R.id.tv_author_url, TextUtils.concat("个人主页：", projectArrayBean.getAuthorUrl()));
        holder.setTextView(R.id.tv_project_name, TextUtils.concat("项目名称：", projectArrayBean.getProjectName()));
        holder.setTextView(R.id.tv_desc, TextUtils.concat("简介：", Html.fromHtml(projectArrayBean.getDesc())));
        holder.setTextView(R.id.tv_project_url, projectArrayBean.getProjectUrl());
    }
}
