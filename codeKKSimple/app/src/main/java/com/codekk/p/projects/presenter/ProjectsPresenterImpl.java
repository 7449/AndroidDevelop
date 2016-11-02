package com.codekk.p.projects.presenter;

import com.codekk.p.projects.model.ProjectsModel;
import com.codekk.p.projects.view.ProjectsView;

import framework.base.BasePresenterImpl;
import framework.network.NetWorkRequest;
import framework.network.NetWorkSubscriber;

/**
 * by y on 2016/8/30.
 */
public class ProjectsPresenterImpl extends BasePresenterImpl<ProjectsView> implements ProjectsPresenter {

    public ProjectsPresenterImpl(ProjectsView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(final int page) {
        view.showProgress();
        //暂时为1，后缀不管是多少，目前得到的数据一直都是最多的
        NetWorkRequest.getProjects(page, 1, new NetWorkSubscriber<ProjectsModel>() {
            @Override
            public void onNext(ProjectsModel projectsModel) {
                super.onNext(projectsModel);
                view.hideProgress();
                if (projectsModel.getProjectArray().isEmpty()) {
                    view.noMore();
                } else {
                    if (page == 1) {
                        view.adapterRemove();
                    }
                    view.setData(projectsModel.getProjectArray());
                }
            }
        });
    }

    @Override
    protected void netWorkError() {
        view.netWorkError();
        view.hideProgress();
    }
}
