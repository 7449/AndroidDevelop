package com.common.util;

import android.view.View;

/**
 * @author y
 */
public class ViewUtils {

    public static void goneView(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    public static void visibleView(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void inVisibleView(View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

}
