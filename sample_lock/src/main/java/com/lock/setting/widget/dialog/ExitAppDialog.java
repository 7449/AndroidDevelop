package com.lock.setting.widget.dialog;


import androidx.appcompat.app.AlertDialog;

import com.lock.R;

import framework.base.BaseDialogFragment;

/**
 * by y on 2017/2/16
 */

public class ExitAppDialog extends BaseDialogFragment {

    private ExitAppListener mAppListener;


    public static ExitAppDialog newInstance() {
        return new ExitAppDialog();
    }

    @Override
    public AlertDialog getDialog() {
        mAppListener = (ExitAppListener) getParentFragment();
        mAlertDialog = new AlertDialog
                .Builder(getActivity())
                .setTitle(getString(R.string.dialog_exit_app))
                .setMessage(getString(R.string.dialog_exit_message))
                .setNegativeButton(getString(R.string.dialog_exit_cancel), null)
                .setPositiveButton(getString(R.string.dialog_exit_positive), (dialog, which) -> {
                    if (mAppListener != null) {
                        mAppListener.onExitApp();
                    }
                })
                .create();
        return mAlertDialog;
    }

    @Override
    protected boolean getCancelable() {
        return true;
    }

    public interface ExitAppListener {
        void onExitApp();
    }

}
