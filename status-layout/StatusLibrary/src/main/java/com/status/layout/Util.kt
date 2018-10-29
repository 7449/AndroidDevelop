package com.status.layout

import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

/**
 * by y on 14/07/2017.
 */

internal object Util {


    val params: FrameLayout.LayoutParams
        get() = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER)

    fun getViewLayout(statusLayout: StatusLayout, @LayoutRes id: Int): View {
        return LayoutInflater.from(statusLayout.context).inflate(id, statusLayout, false)
    }

    fun goneView(vararg views: View?) {
        for (view in views) {
            if (view != null && view.visibility != View.GONE) {
                view.visibility = View.GONE
            }
        }
    }

    fun visibilityView(view: View?) {
        if (view != null && view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
    }
}
