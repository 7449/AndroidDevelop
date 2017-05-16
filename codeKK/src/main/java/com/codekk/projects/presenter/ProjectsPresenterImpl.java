package com.codekk.projects.presenter;

import com.codekk.projects.model.ProjectsModel;
import com.codekk.projects.view.ProjectsView;
import com.rxnetwork.manager.RxNetWork;

import framework.base.BasePresenterImpl;
import framework.network.Api;
import framework.network.NetWorkFunc;

/**
 * by y on 2016/8/30.
 */
public class ProjectsPresenterImpl extends BasePresenterImpl<ProjectsView, ProjectsModel>
        implements ProjectsPresenter {

    public ProjectsPresenterImpl(ProjectsView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(final int page) {
        netWork(RxNetWork
                .observable(Api.CodeKKService.class)
                .getProjects(page, 1)
                .map(new NetWorkFunc<ProjectsModel>()));
    }

    @Override
    protected void netWorkError() {
        view.netWorkError();
        view.hideProgress();
    }

    @Override
    public void onNetWorkSuccess(ProjectsModel data) {
        if (data.getProjectArray().isEmpty()) {
            view.noMore();
        } else {
            view.setData(data.getProjectArray());
        }
    }
}
