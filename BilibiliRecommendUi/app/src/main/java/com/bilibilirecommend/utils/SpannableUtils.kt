package com.bilibilirecommend.utils


import android.support.v4.content.ContextCompat
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan

import com.bilibilirecommend.App
import com.bilibilirecommend.R


object SpannableUtils {

    fun getHomeCountText(text: Int): SpannableStringBuilder {
        val mText = SpannableStringBuilder("当前" + text + "个直播")
        val foregroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(App.instance, R.color.colorPrimary))
        mText.setSpan(foregroundColorSpan, 2, mText.length - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return mText
    }

    fun getHomeTitlePageType(text: String, suffix: String): SpannableStringBuilder {
        val mText = SpannableStringBuilder("#$text#$suffix")
        val foregroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(App.instance, R.color.colorPrimary))
        mText.setSpan(foregroundColorSpan, 0, mText.length - suffix.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return mText
    }

}
