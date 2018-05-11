package com.fuckapp.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * by y on 2016/10/31
 */

public abstract class BaseDialog extends AlertDialog {

    public View view;

    protected BaseDialog(@NonNull Context context) {
        super(context);
        onCreateView();
        initById();
        if (view != null) {
            setView(view);
            show();
        }
    }

    protected abstract void onCreateView();

    protected abstract void initById();

}