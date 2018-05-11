package com.lock.checkapp.presenter;

import android.content.Context;

import com.lock.R;
import com.lock.checkapp.view.CheckView;

import java.util.List;

import framework.base.BasePresenterImpl;
import scan.AppModel;
import scan.ScanAppUtils;
import scan.listener.ScanListener;

/**
 * by y on 2017/2/16
 */

public class CheckPresenterImpl extends BasePresenterImpl<CheckView>
        implements CheckPresenter, ScanListener {
    public CheckPresenterImpl(CheckView view) {
        super(view);
    }

    @Override
    public void onScanApp() {
        ScanAppUtils.newInstance().setNoSystem(true).start(ScanAppUtils.ALL_APP, this);
    }

    @Override
    public void onClick(int id) {
        switch (id) {
            case R.id.check_save_app:
                view.insertDB();
                break;
            case R.id.check_save_app_all:
                view.clearDb();
                view.insertDB();
                break;
        }
    }

    @Override
    public void onScanStart() {
        view.showProgressBar();
        view.removeAdapter();
    }

    @Override
    public void onScanSuccess(List<AppModel> data) {
        view.onScanSuccess(data);
        view.hideProgressBar();
    }

    @Override
    public void onScanError(Throwable e) {
        view.onScanError(e);
    }

    @Override
    public Context getScanContext() {
        return view.getScanContext();
    }
}
