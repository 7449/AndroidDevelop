package com.android.mvp.lifecycle.base

import androidx.lifecycle.DefaultLifecycleObserver

/**
 * @author y
 * @create 2019/3/31
 */
interface BaseIPresenter : DefaultLifecycleObserver {
    fun onDestroy()
}
