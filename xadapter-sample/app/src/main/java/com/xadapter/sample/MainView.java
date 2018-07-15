package com.xadapter.sample;

import java.util.List;

/**
 * by y.
 * <p>
 * Description:
 */
public interface MainView {

    void onChangeRootLayoutStatus(String status);

    void onRemoveAll();

    void onNetComplete(int type);

    void onNetError(int type);

    void onLoadNoMore();

    void onNetSuccess(List<Entity> entity);

    void onPagePlus();

    int getPage();

}
