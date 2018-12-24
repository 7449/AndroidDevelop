package com.status.layout

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.StringDef

@StringDef(NORMAL, LOADING, EMPTY, SUCCESS, ERROR)
@Retention(AnnotationRetention.SOURCE)
annotation class StatusAnnotation

const val NORMAL = "StatusLayout:Normal"
const val LOADING = "StatusLayout:Loading"
const val EMPTY = "StatusLayout:Empty"
const val SUCCESS = "StatusLayout:Success"
const val ERROR = "StatusLayout:Error"


interface OnStatusClickListener {
    fun onNorMalClick(view: View)

    fun onLoadingClick(view: View)

    fun onEmptyClick(view: View)

    fun onSuccessClick(view: View)

    fun onErrorClick(view: View)
}

open class SimpleOnStatusClickListener : OnStatusClickListener {

    override fun onNorMalClick(view: View) {

    }

    override fun onLoadingClick(view: View) {

    }

    override fun onEmptyClick(view: View) {

    }

    override fun onSuccessClick(view: View) {

    }

    override fun onErrorClick(view: View) {

    }
}

val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER)

fun getViewLayout(statusLayout: StatusLayout, @LayoutRes id: Int): View = LayoutInflater.from(statusLayout.context).inflate(id, statusLayout, false)

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
