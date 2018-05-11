package com.dagger.application;

import android.app.Application;

import javax.inject.Inject;

/**
 * by y on 2017/5/31.
 */

public class App extends Application {


    @Inject
    App app;
    private ApplicationComponent build;

    @Override
    public void onCreate() {
        super.onCreate();
        build = DaggerApplicationComponent
                .builder()
                .appModel(new AppModel(this))
                .build();
        build.register(this);
    }


    public static ApplicationComponent getBuild(App app) {
        return app.build;
    }
}
