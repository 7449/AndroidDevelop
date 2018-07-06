package com.common.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

public abstract class CommonDialogFragment extends DialogFragment {

    protected View mRootView = null;
    protected AlertDialog mAlertDialog = null;
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(getCancelable());
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, getStyle());
        mRootView = getRootView(getLayoutId());
        builder.setView(mRootView);
        mAlertDialog = builder.create();
        return mAlertDialog;
    }


    public View getRootView(int id) {
        return View.inflate(getActivity(), id, null);
    }

    public abstract int getLayoutId();

    protected abstract int getStyle();

    protected abstract boolean getCancelable();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRootView != null) {
            mRootView = null;
        }
    }
}
