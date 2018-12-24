package com.bilibilirecommend.utils

import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.bilibilirecommend.App


object CompoundDrawableUtils {

    fun setRecommend(textView: TextView, id: Int) {
        val drawable = ContextCompat.getDrawable(App.instance, id)
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        textView.setCompoundDrawables(drawable, null, null, null)
    }

    fun setLive(textView: TextView, id: Int) {
        val drawable = ContextCompat.getDrawable(App.instance, id)
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        textView.setCompoundDrawables(null, null, drawable, null)
    }

    fun setTitle(textView: TextView, id: Int) {
        val drawable = ContextCompat.getDrawable(App.instance, id)
        drawable?.setBounds(0, 0, 60, 60)
        textView.setCompoundDrawables(drawable, null, null, null)
    }

    fun setItem(textView: TextView, id: Int) {
        val drawable = ContextCompat.getDrawable(App.instance, id)
        drawable?.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        textView.setCompoundDrawables(drawable, null, null, null)
    }
}
