package com.bilirecommendui.network;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.bilirecommendui.App;

/**
 * by y on 2016/9/17.
 */
public class CompoundDrawableUtils {


    public static void setSrc(TextView textView, int id) {
        Drawable drawable = ContextCompat.getDrawable(App.getContext(), id);
        drawable.setBounds(0, 0, 50, 50);
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setFanDramaSrc(TextView textView, int id) {
        Drawable drawable = ContextCompat.getDrawable(App.getContext(), id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setPartitionSrc(TextView textView, int id) {
        Drawable drawable = ContextCompat.getDrawable(App.getContext(), id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }
}
