package com.dagger.application;

import dagger.Module;
import dagger.Provides;

/**
 * by y on 2017/5/31.
 */
@Module
public class AppModel {

    private App app;

    public AppModel(App app) {
        this.app = app;
    }

    @Provides
    public App get() {
        return app;
    }
}
