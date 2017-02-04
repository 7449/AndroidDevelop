package com.fuckapp.fragment.view;

import com.fuckapp.fragment.model.AppModel;

import java.util.List;

/**
 * by y on 2016/10/31
 */

public interface AppView {
    void removeAllAdapter();

    void setAppInfo(List<AppModel> appInfo);

    void showProgress();

    void hideProgress();

    void obtainSuccess();

    void obtainError();
}
