@file:Suppress("FunctionName", "unused", "ShowToast")

package com.kotlin.x

import android.content.Context
import android.os.Build
import android.support.v4.app.Fragment
import android.util.Log

private const val DEFAULT_LOG_TAG = "KotlinX"

fun Context.Toast(any: Any) = android.widget.Toast.makeText(this, any.toString(), android.widget.Toast.LENGTH_SHORT).show()

fun Fragment.Toast(any: Any) = activity?.let { android.widget.Toast.makeText(it, any.toString(), android.widget.Toast.LENGTH_SHORT).show() }

fun android.app.Fragment.Toast(any: Any) = activity?.let { android.widget.Toast.makeText(it, any.toString(), android.widget.Toast.LENGTH_SHORT).show() }

fun Fragment.currentActivity() = activity?.let { it } ?: throw NullPointerException()

fun android.app.Fragment.currentActivity() = activity?.let { it } ?: throw NullPointerException()

fun Any.minVersion(version: Int) = Build.VERSION.SDK_INT >= version

fun Any.maxVersion(version: Int) = Build.VERSION.SDK_INT <= version

fun Any.currentVersion(version: Int) = Build.VERSION.SDK_INT == version

fun Any.LogV() = LogV(this.toString())

fun Any.LogV(any: Any) = LogV(DEFAULT_LOG_TAG, any)

fun Any.LogV(tag: String, any: Any) = Log.v(tag, any.toString())

fun Any.LogD() = LogD(this.toString())

fun Any.LogD(any: Any) = LogD(DEFAULT_LOG_TAG, any)

fun Any.LogD(tag: String, any: Any) = Log.d(tag, any.toString())

fun Any.LogI() = LogI(this.toString())

fun Any.LogI(any: Any) = LogI(DEFAULT_LOG_TAG, any)

fun Any.LogI(tag: String, any: Any) = Log.i(tag, any.toString())

fun Any.LogW() = LogW(this.toString())

fun Any.LogW(any: Any) = LogW(DEFAULT_LOG_TAG, any)

fun Any.LogW(tag: String, any: Any) = Log.w(tag, any.toString())

fun Any.LogE() = LogE(this.toString())

fun Any.LogE(any: Any) = LogE(DEFAULT_LOG_TAG, any)

fun Any.LogE(tag: String, any: Any) = Log.e(tag, any.toString())

fun Any.LogWtf() = LogWtf(this.toString())

fun Any.LogWtf(any: Any) = LogWtf(DEFAULT_LOG_TAG, any)

fun Any.LogWtf(tag: String, any: Any) = Log.wtf(tag, any.toString())