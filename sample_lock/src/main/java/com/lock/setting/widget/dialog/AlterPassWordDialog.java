package com.lock.setting.widget.dialog;


import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.lock.R;

import framework.base.BaseDialogFragment;

/**
 * by y on 2017/2/16
 */

public class AlterPassWordDialog extends BaseDialogFragment {

    private AlterPassWordListener mListener;
    private TextInputEditText mPassWord;

    public static AlterPassWordDialog newInstance() {
        return new AlterPassWordDialog();
    }

    @Override
    public AlertDialog getDialog() {
        mListener = (AlterPassWordListener) getParentFragment();
        mRootView = getRootView(R.layout.dialog_alter_pass_word);
        mPassWord = getView(R.id.et_pass_word);
        mAlertDialog = new AlertDialog
                .Builder(getActivity())
                .setTitle(getString(R.string.dialog_alter_pass_title))
                .setView(mRootView)
                .setNegativeButton(getString(R.string.dialog_alter_pass_cancel), null)
                .setPositiveButton(getString(R.string.dialog_alter_pass_negative), (dialog, which) -> {
                    if (mListener != null) {
                        mListener.onAlterPassWord(mPassWord.getText().toString().trim());
                    }
                })
                .create();
        return mAlertDialog;
    }

    @Override
    protected boolean getCancelable() {
        return true;
    }

    public interface AlterPassWordListener {
        void onAlterPassWord(String newPassWord);
    }
}
