package com.api;

import android.view.View;

/**
 * by y.
 * <p>
 * Description:
 */
public interface ViewBind<T> {

    void bindView(final T target, View view);

    void unBind();
}