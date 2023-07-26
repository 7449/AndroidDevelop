package com.lock.main.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.lock.R;

import framework.base.BaseDialogFragment;
import framework.utils.SPUtils;

/**
 * by y on 2017/2/16
 */

public class VerifyFragment extends BaseDialogFragment implements View.OnClickListener {

    private static final String TYPE = "type";
    private TextInputEditText mPassWord;
    private VerifyClick mVerifyClick = null;
    private int type;

    public static VerifyFragment newInstance(int type) {
        VerifyFragment verifyFragment = new VerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        verifyFragment.setArguments(bundle);
        return verifyFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mVerifyClick = (VerifyClick) context;
        Bundle arguments = getArguments();
        type = arguments.getInt(TYPE);
    }

    @Override
    public AlertDialog getDialog() {
        mRootView = getRootView(R.layout.fragment_verify);
        mAlertDialog = new AlertDialog.
                Builder(getActivity()).
                setTitle(getString(R.string.dialog_verify_title) + SPUtils.INSTANCE.getUserName()).
                setView(mRootView)
                .create();
        AppCompatButton button = getView(R.id.btn_register);
        button.setOnClickListener(this);
        button.setText(getString(R.string.dialog_verify_start));
        getView(R.id.btn_cancel).setOnClickListener(this);
        mPassWord = getView(R.id.et_pass_word);
        return mAlertDialog;
    }


    @Override
    protected boolean getCancelable() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String passWord = mPassWord.getText().toString().trim();
                if (!TextUtils.equals(passWord, SPUtils.INSTANCE.getPassWord())) {
                    mVerifyClick.onVerifyError();
                } else {
                    if (mVerifyClick != null) {
                        mVerifyClick.onVerifySuccess(type);
                        mAlertDialog.dismiss();
                    }
                }
                break;
            case R.id.btn_cancel:
                mAlertDialog.dismiss();
                break;
        }
    }

    public interface VerifyClick {
        void onVerifySuccess(int type);

        void onVerifyError();
    }

}
