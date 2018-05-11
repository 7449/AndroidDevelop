package com.lock.setting.presenter;

import com.lock.setting.view.SettingView;

import framework.base.BasePresenterImpl;

/**
 * by y on 2017/2/16
 */

public class SettingPresenterImpl extends BasePresenterImpl<SettingView> implements SettingPresenter {

    public SettingPresenterImpl(SettingView view) {
        super(view);
    }

    @Override
    public void itemClick(int position) {
        switch (position) {
            case 0:
                view.clearSetting();
                break;
            case 1:
                view.deleteApp();
                break;
            case 2:
                view.alterPassWord();
                break;
            case 3:
                view.exitApp();
                break;
            default:
                break;
        }
    }
}
