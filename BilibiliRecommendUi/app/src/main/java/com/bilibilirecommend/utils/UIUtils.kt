package com.bilibilirecommend.utils


import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

import com.bilibilirecommend.App


object UIUtils {

    val context: Context
        get() = App.instance

    val simpleName: String
        get() = context.javaClass.simpleName


    fun hypo(a: Int, b: Int): Float {
        return Math.sqrt(Math.pow(a.toDouble(), 2.0) + Math.pow(b.toDouble(), 2.0)).toFloat()
    }


    fun offKeyboard(editText: EditText) {
        if (detectKeyboard(editText)) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        }
    }

    fun openKeyboard(editText: EditText) {
        if (!detectKeyboard(editText)) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun detectKeyboard(editText: EditText): Boolean {
        //true 弹出状态
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

}
