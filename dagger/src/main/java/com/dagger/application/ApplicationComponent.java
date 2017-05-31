package com.dagger.application;

import dagger.Component;

/**
 * by y on 2017/5/31.
 */
@Component(modules = {AppModel.class})
public interface ApplicationComponent {
    void register(App app);

    App getApplication();

}
