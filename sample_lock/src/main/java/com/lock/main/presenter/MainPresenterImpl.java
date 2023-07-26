package com.lock.main.presenter;

import com.lock.main.model.MainBean;
import com.lock.main.view.MainView;

import framework.base.BasePresenterImpl;
import framework.utils.SPUtils;

/**
 * by y on 2017/2/16
 */

public class MainPresenterImpl extends BasePresenterImpl<MainView> implements MainPresenter {

    public MainPresenterImpl(MainView view) {
        super(view);
    }

    @Override
    public void registerUser(String userName, String passWord) {
        MainBean userBean = new MainBean();
        userBean.setUserName(userName);
        userBean.setPassWord(passWord);
        SPUtils.INSTANCE.writeUser(userBean);
        view.setUser(SPUtils.INSTANCE.readeUser());

        if (SPUtils.INSTANCE.isLogin()) {
            view.registerError();
        } else {
            view.registerSuccess();
        }

    }

}
