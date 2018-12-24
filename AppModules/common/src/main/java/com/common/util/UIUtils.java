package com.common.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.common.CommonApplication;

public class UIUtils {

    public static Context getContext() {
        return CommonApplication.getInstance();
    }

    public static <T> T getView(@LayoutRes int id) {
        return (T) View.inflate(getContext(), id, null);
    }

    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    public static int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    public static float getDimension(int id) {
        return getContext().getResources().getDimension(id);
    }

    public static void offKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static void openKeyboard(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }
    }


    public static void forceOffKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
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


    public static void startActivity(Activity activity, Class<?> clz, int code, Bundle bundle) {
        activity.startActivityForResult(new Intent(getContext(), clz).putExtras(bundle), code);
    }

    public static void startActivity(Fragment fragment, Class<?> clz, int code, Bundle bundle) {
        fragment.startActivityForResult(new Intent(getContext(), clz).putExtras(bundle), code);
    }

    public static void startActivity(Object object, Class<?> clz, int code, Bundle bundle) {
        if (object instanceof Activity) {
            startActivity((Activity) object, clz, code, bundle);
        } else if (object instanceof Fragment) {
            startActivity((Fragment) object, clz, code, bundle);
        }
    }

    public static void hideStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
    }

    public static void showStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void gotoStore() {
        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            goToMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(goToMarket);
        } catch (ActivityNotFoundException ignored) {
        }
    }
}
