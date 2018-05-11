package com.camera.data;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * by y on 2016/11/21
 */

public class StatusBarUtil {

    private static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    public static void setTranslucentForImageView(Activity act, View marginView) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup.MarginLayoutParams vmlp = (ViewGroup.MarginLayoutParams) marginView.getLayoutParams();
            vmlp.setMargins(0, -getStatusBarHeight(act), 0, 0);
        }
    }

}