package com.api;

import android.view.View;

/**
 * by y.
 * <p>
 * Description:
 */
public interface ViewBind<T> {

    void bindView(T target, View view);

    void unBind();
}