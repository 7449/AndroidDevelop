package com.codekk.p.projects.view;

import com.codekk.p.projects.model.ProjectsModel;

import java.util.List;

import framework.base.BaseView;

/**
 * by y on 2016/8/30.
 */
public interface ProjectsView extends BaseView {
    void setData(List<ProjectsModel.ProjectArrayBean> projectArray);

    void noMore();

    void adapterRemove();
}
