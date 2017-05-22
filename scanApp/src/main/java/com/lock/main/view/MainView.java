package com.lock.main.view;

import com.lock.main.model.MainBean;

/**
 * by y on 2017/2/16
 */

public interface MainView {

    void setUser(MainBean user);

    void registerSuccess();

    void registerError();
}
