package com.dagger.mvp.view;


import com.dagger.application.App;
import com.dagger.application.ApplicationComponent;
import com.dagger.mvp.model.RegisterModel;
import com.dagger.mvp.widget.MVPActivity;

import dagger.Component;

/**
 * by y on 2017/5/31.
 */
@Component(dependencies = ApplicationComponent.class, modules = RegisterModel.class)
public interface MVPComponent {
    void register(MVPActivity activity);

    App getApplication();
}
