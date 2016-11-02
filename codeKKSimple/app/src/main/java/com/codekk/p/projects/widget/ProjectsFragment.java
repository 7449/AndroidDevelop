package com.codekk.p.projects.widget;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;

import com.codekk.p.R;
import com.codekk.p.projects.model.ProjectsModel;
import com.codekk.p.projects.presenter.ProjectsPresenter;
import com.codekk.p.projects.presenter.ProjectsPresenterImpl;
import com.codekk.p.projects.view.ProjectsView;

import java.util.LinkedList;
import java.util.List;

import framework.base.BaseFragment;
import framework.base.BaseRecyclerViewAdapter;
import framework.data.Constant;
import framework.utils.UIUtils;
import framework.widget.MRecyclerView;

/**
 * by y on 2016/8/30.
 */
public class ProjectsFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        ProjectsView, MRecyclerView.LoadingData {

    private MRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private ProjectsAdapter mAdapter;

    private ProjectsPresenter mPresenter;

    private int page;

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Override
    protected void initById() {
        mRecyclerView = getView(R.id.recyclerView);
        mSwipeRefresh = getView(R.id.srf_layout);
    }

    @Override
    protected void initData() {
        mPresenter = new ProjectsPresenterImpl(this);
        List<ProjectsModel.ProjectArrayBean> list = new LinkedList<>();
        mAdapter = new ProjectsAdapter(list);

        mRecyclerView.setLoadingData(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(Constant.RECYCLERVIEW_LISTVIEW, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
    }

    @Override
    protected void toolbarOnclick() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void setData(List<ProjectsModel.ProjectArrayBean> projectArray) {
        mAdapter.addAll(projectArray);
    }

    @Override
    public void noMore() {
        UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.recyclerview_data_null));
    }

    @Override
    public void adapterRemove() {
        mAdapter.removeAll();
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
        UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.network_error));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSwipeRefresh.setRefreshing(false);
    }

    public class ProjectsAdapter extends BaseRecyclerViewAdapter<ProjectsModel.ProjectArrayBean> {

        public ProjectsAdapter(List<ProjectsModel.ProjectArrayBean> mDatas) {
            super(mDatas);
        }

        @Override
        protected int getItemLayoutId() {
            return R.layout.item_projects;
        }

        @Override
        protected void onBind(ViewHolder holder, int position, ProjectsModel.ProjectArrayBean data) {
            holder.setTextView(R.id.tv_author_name, "开源者：" + data.getAuthorName());
            holder.setTextView(R.id.tv_author_url, "个人主页：" + data.getAuthorUrl());
            holder.setTextView(R.id.tv_project_name, "项目名称：" + data.getProjectName());
            //noinspection deprecation
            holder.setTextView(R.id.tv_desc, "简介：" + Html.fromHtml(data.getDesc()));
            holder.setTextView(R.id.tv_project_url, data.getProjectUrl());
        }
    }

}
