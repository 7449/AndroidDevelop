package com.bilibilirecommend.utils;


import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.bilibilirecommend.App;
import com.bilibilirecommend.R;


/**
 * by y on 2016/9/16.
 */
public class SpannableUtils {

    public static SpannableStringBuilder getHomeCountText(int text) {
        SpannableStringBuilder mText = new SpannableStringBuilder("当前" + text + "个直播");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(App.getContext(), R.color.colorPrimary));
        mText.setSpan(foregroundColorSpan, 2, mText.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return mText;
    }

    public static SpannableStringBuilder getHomeTitlePageType(String text, String suffix) {
        SpannableStringBuilder mText = new SpannableStringBuilder("#" + text + "#" + suffix);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(App.getContext(), R.color.colorPrimary));
        mText.setSpan(foregroundColorSpan, 0, mText.length() - suffix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return mText;
    }

}
