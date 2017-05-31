package com.dagger.mvp.model;

import android.support.annotation.NonNull;

import com.dagger.mvp.view.MVPView;

import dagger.Module;
import dagger.Provides;

/**
 * by y on 2017/5/31.
 */
@Module
public class RegisterModel {

    @NonNull
    private final MVPView mvpView;

    public RegisterModel(@NonNull MVPView mvpView) {
        this.mvpView = mvpView;
    }

    @Provides
    MVPView provideView() {
        return mvpView;
    }
}
