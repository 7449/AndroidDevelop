package com.readlist.main.presenter;

import com.readlist.R;
import com.readlist.main.view.MainView;

/**
 * by y on 2016/11/8
 */

public class MainPresenterImpl implements MainPresenter {

    private final MainView mainView;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void switchId(int id) {
        switch (id) {
            case R.id.weixin:
                mainView.switchWeixin();
                break;
            case R.id.picture:
                mainView.switchPicture();
                break;
            case R.id.news:
                mainView.switchNews();
                break;
        }
    }
}
