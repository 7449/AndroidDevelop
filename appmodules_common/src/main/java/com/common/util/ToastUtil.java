package com.common.util;

import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * by y on 2017/3/7
 */

public class ToastUtil {
    private static Toast TOAST;

    public static void show(@StringRes int resourceId) {
        show(resourceId, Toast.LENGTH_SHORT);
    }

    public static void show(@NonNull String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(@StringRes int resourceId, int duration) {
        String text = UIUtils.getContext().getResources().getString(resourceId);
        show(text, duration);
    }

    public static void show(@NonNull String text, int duration) {
        try {
            if (TOAST == null) {
                TOAST = Toast.makeText(UIUtils.getContext(), text, duration);
            } else {
                TOAST.setText(text);
            }
            TOAST.setDuration(duration);
            TOAST.show();
        } catch (Exception e) {
            Looper.prepare();
            Toast.makeText(UIUtils.getContext(), text, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }


    public static void hide() {
        if (TOAST != null) {
            TOAST.cancel();
        }
    }
}

