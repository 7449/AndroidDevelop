package com.api;

/**
 * by y.
 * <p>
 * Description:
 */
public interface ViewBind<T> {

    void bindView(T view);

    void unBind();
}