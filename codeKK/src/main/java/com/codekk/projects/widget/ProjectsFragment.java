package com.codekk.projects.widget;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.TextUtils;

import com.codekk.R;
import com.codekk.projects.model.ProjectsModel;
import com.codekk.projects.presenter.ProjectsPresenter;
import com.codekk.projects.presenter.ProjectsPresenterImpl;
import com.codekk.projects.view.ProjectsView;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import java.util.List;

import butterknife.BindView;
import framework.base.BaseFragment;
import framework.data.Constant;
import framework.utils.UIUtils;
import framework.widget.MRecyclerView;

/**
 * by y on 2016/8/30.
 */
public class ProjectsFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        ProjectsView, MRecyclerView.LoadingListener, OnXBindListener<ProjectsModel.ProjectArrayBean> {

    @BindView(R.id.recyclerView)
    MRecyclerView mRecyclerView;
    @BindView(R.id.srf_layout)
    SwipeRefreshLayout mSwipeRefresh;

    private XRecyclerViewAdapter<ProjectsModel.ProjectArrayBean> mAdapter;
    private ProjectsPresenter mPresenter;
    private int page;


    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }


    @Override
    protected void initActivityCreated() {
        mPresenter = new ProjectsPresenterImpl(this);
        mAdapter = new XRecyclerViewAdapter<>();
        mRecyclerView.setLoadingData(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LISTVIEW, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter
                .setLayoutId(R.layout.item_projects)
                .onXBind(this));

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void setData(List<ProjectsModel.ProjectArrayBean> projectArray) {
        if (page == 1) {
            mAdapter.removeAll();
        }
        mAdapter.addAllData(projectArray);
    }

    @Override
    public void noMore() {
        UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.recyclerview_data_null));
    }


    @Override
    public void onRefresh() {
        page = 1;
        netWork();
    }

    @Override
    public void onScrolledToBottom() {
        ++page;
        netWork();
    }

    private void netWork() {
        mPresenter.netWorkRequest(page);
    }

    @Override
    public void showProgress() {
        mSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void netWorkError() {
        UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.network_error));
    }

    @Override
    public void onToolbarClick() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onXBind(XViewHolder holder, int position, ProjectsModel.ProjectArrayBean projectArrayBean) {
        holder.setTextView(R.id.tv_author_name, TextUtils.concat("开源者：", projectArrayBean.getAuthorName()));
        holder.setTextView(R.id.tv_author_url, TextUtils.concat("个人主页：", projectArrayBean.getAuthorUrl()));
        holder.setTextView(R.id.tv_project_name, TextUtils.concat("项目名称：", projectArrayBean.getProjectName()));
        holder.setTextView(R.id.tv_desc, TextUtils.concat("简介：", Html.fromHtml(projectArrayBean.getDesc())));
        holder.setTextView(R.id.tv_project_url, projectArrayBean.getProjectUrl());
    }
}
