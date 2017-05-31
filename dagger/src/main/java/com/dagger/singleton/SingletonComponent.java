package com.dagger.singleton;

import javax.inject.Singleton;

import dagger.Component;

/**
 * by y on 2017/5/31.
 */
@Singleton
@Component
public interface SingletonComponent {
    void register(SingletonActivity activity);
}
