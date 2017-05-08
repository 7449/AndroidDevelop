package com.bilirecommendui.utils;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.bilirecommendui.App;

/**
 * by y on 2016/9/17.
 */
public class CompoundDrawableUtils {


    public static void setRecommend(TextView textView, int id) {
        Drawable drawable = ContextCompat.getDrawable(App.getContext(), id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setLive(TextView textView, int id) {
        Drawable drawable = ContextCompat.getDrawable(App.getContext(), id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    public static void setTitle(TextView textView, int id) {
        Drawable drawable = ContextCompat.getDrawable(App.getContext(), id);
        drawable.setBounds(0, 0, 60, 60);
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setItem(TextView textView, int id) {
        Drawable drawable = ContextCompat.getDrawable(App.getContext(), id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }
}
