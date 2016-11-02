package framework.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.ClipboardManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import framework.App;
import framework.base.BaseActivity;


/**
 * by y on 2016/8/7.
 */
public class UIUtils {


    public static Context getContext() {
        return App.getInstance();
    }

    public static Activity getActivity() {
        return BaseActivity.getActivity();
    }

    public static int getColor(int id) {
        //noinspection deprecation
        return getContext().getResources().getColor(id);
    }

    public static void copy(String content) {
        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(content);
    }

    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    public static String getSimpleName() {
        return getContext().getClass().getSimpleName();
    }


    public static View getInflate(int layout) {
        return View.inflate(getActivity(), layout, null);
    }

    public static void startActivity(Class<?> clz) {
        Intent intent = new Intent(getContext(), clz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    public static void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    public static void offKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void Toast(Object object) {
        Toast.makeText(getContext(), object + "", Toast.LENGTH_LONG).show();
    }

    public static void SnackBar(View view, Object object) {
        Snackbar.make(view, object + "", Snackbar.LENGTH_SHORT).show();
    }

    public static void SnackBar(View view, Object object, int color) {
        Snackbar.make(view, object + "", Snackbar.LENGTH_SHORT)
                .setActionTextColor(color)
                .show();
    }

}
