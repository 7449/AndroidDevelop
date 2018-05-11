package com.lock.main.widget;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.lock.R;

import framework.base.BaseDialogFragment;

/**
 * by y on 2017/2/15
 */

public class LoginFragment extends BaseDialogFragment
        implements View.OnClickListener {

    private TextInputEditText mUserName;
    private TextInputEditText mPassWord;
    private LoginClick mLoginClick = null;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLoginClick = (LoginClick) context;
    }

    @Override
    public AlertDialog getDialog() {
        mRootView = getRootView(R.layout.dialog_login);
        mAlertDialog = new AlertDialog
                .Builder(getActivity())
                .setTitle(getString(R.string.dialog_login_title))
                .setView(mRootView)
                .create();
        getView(R.id.btn_register).setOnClickListener(this);
        getView(R.id.btn_cancel).setOnClickListener(this);
        mUserName = getView(R.id.et_user_name);
        mPassWord = getView(R.id.et_pass_word);
        return mAlertDialog;
    }

    @Override
    protected boolean getCancelable() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                String passWord = mPassWord.getText().toString().trim();
                String userName = mUserName.getText().toString().trim();
                if (TextUtils.isEmpty(passWord) || TextUtils.isEmpty(userName)) {
                    Toast.makeText(getContext(), R.string.dialog_login_fail, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mLoginClick != null) {
                    mLoginClick.onRegister(userName, passWord);
                    mAlertDialog.dismiss();
                }
                break;
            case R.id.btn_cancel:
                if (mLoginClick != null) {
                    mAlertDialog.dismiss();
                    mLoginClick.onCancel();
                }
                break;
        }
    }

    public interface LoginClick {
        void onRegister(String userName, String passWord);

        void onCancel();
    }
}
