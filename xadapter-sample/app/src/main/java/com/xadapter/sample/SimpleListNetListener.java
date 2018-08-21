package com.xadapter.sample;

import android.util.Log;

import com.xadapter.sample.status.StatusLayout;

import java.util.List;

import io.reactivex.network.RxNetWorkListener;

/**
 * by y.
 * <p>
 * Description:
 */
public class SimpleListNetListener implements RxNetWorkListener<List<Entity>> {

    private final MainView mainView;
    private final int page;
    private final int type;

    SimpleListNetListener(MainView mainView, int type) {
        if (mainView == null) {
            throw new NullPointerException();
        }
        this.mainView = mainView;
        this.page = mainView.getPage();
        this.type = type;
    }

    @Override
    public void onNetWorkStart() {
        if (type == SimpleAdapter.TYPE_STATUS) {
            mainView.onChangeRootLayoutStatus(StatusLayout.Status.LOADING);
        }
    }

    @Override
    public void onNetWorkError(Throwable e) {
        Log.d(getClass().getSimpleName(), e.getMessage());
        if (page == 0 && type == SimpleAdapter.TYPE_STATUS) {
            mainView.onChangeRootLayoutStatus(StatusLayout.Status.ERROR);
        } else {
            mainView.onNetError(type);
        }
    }

    @Override
    public void onNetWorkComplete() {
    }

    @Override
    public void onNetWorkSuccess(List<Entity> data) {
        Log.d(getClass().getSimpleName(), "success");
        if (data == null) {
            onNetWorkError(new NullPointerException());
            return;
        }
        if (page == 0) {
            mainView.onRemoveAll();
        }
        if (data.isEmpty()) {
            if (page == 0) {
                mainView.onChangeRootLayoutStatus(StatusLayout.Status.EMPTY);
            } else {
                mainView.onLoadNoMore();
            }
        } else {
            mainView.onNetSuccess(data);
            mainView.onPagePlus();
            if (page == 0 && type == SimpleAdapter.TYPE_STATUS) {
                mainView.onChangeRootLayoutStatus(StatusLayout.Status.SUCCESS);
            } else {
                mainView.onNetComplete(type);
            }
        }
    }
}
