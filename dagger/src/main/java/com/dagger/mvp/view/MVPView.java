package com.dagger.mvp.view;

import com.dagger.mvp.model.MVPBean;

import java.util.List;

/**
 * by y on 2017/5/31.
 */

public interface MVPView {
    void showProgress();

    void hideProgress();

    void onNetError();

    void onNetSuccess(List<MVPBean> list);
}
