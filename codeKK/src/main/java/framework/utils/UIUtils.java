package framework.utils;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.ClipboardManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import framework.App;


/**
 * by y on 2016/8/7.
 */
public class UIUtils {


    public static Context getContext() {
        return App.getInstance();
    }


    public static int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    public static void copy(String content) {
        ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(content);
    }

    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    public static View getInflate(int layout) {
        return View.inflate(getContext(), layout, null);
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
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void toast(Object object) {
        Toast.makeText(getContext(), object + "", Toast.LENGTH_LONG).show();
    }

    public static void snackBar(View view, Object object) {
        Snackbar.make(view, object + "", Snackbar.LENGTH_SHORT).show();
    }

    public static void snackBar(View view, Object object, int color) {
        Snackbar.make(view, object + "", Snackbar.LENGTH_SHORT)
                .setActionTextColor(color)
                .show();
    }

}
