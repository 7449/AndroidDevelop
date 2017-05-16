package com.codekk.projects.presenter;

import com.codekk.projects.model.ProjectsModel;
import com.codekk.projects.view.ProjectsView;

import framework.base.BasePresenterImpl;
import framework.network.NetWork;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        NetWork
                .getCodeKK()
                .getProjects(page, 1)
                .map(new NetWork.NetWorkResultFunc<ProjectsModel>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProjectsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        netWorkError();
                    }

                    @Override
                    public void onNext(ProjectsModel projectsModel) {
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
