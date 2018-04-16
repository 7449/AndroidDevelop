package com.common.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * by y on 2017/3/7
 */

class ToastUtil {
    private static Toast TOAST;

    public static void show(Context context, @StringRes int resourceId) {
        show(context, resourceId, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, @NonNull String text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, @StringRes int resourceId, int duration) {
        String text = context.getResources().getString(resourceId);
        show(context, text, duration);
    }

    public static void show(Context context, @NonNull String text, int duration) {
        if (TOAST == null) {
            TOAST = Toast.makeText(context, text, duration);
        } else {
            TOAST.setText(text);
            TOAST.setDuration(duration);
        }
        TOAST.show();
    }


    public static void hide() {
        if (TOAST != null) {
            TOAST.cancel();
        }
    }
}

