package com.lock.checkapp.view;

import android.content.Context;

import java.util.List;

import scan.AppModel;

/**
 * by y on 2017/2/16
 */

public interface CheckView {

    void showProgressBar();

    void hideProgressBar();

    void onScanSuccess(List<AppModel> data);

    void onScanError(Throwable e);

    void removeAdapter();

    Context getScanContext();

    void clearDb();

    void insertDB();

}
