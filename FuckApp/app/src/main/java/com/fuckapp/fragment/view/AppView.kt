package com.fuckapp.fragment.view

import com.fuckapp.fragment.model.AppModel

/**
 * by y on 2016/10/31
 */

interface AppView {
    fun removeAllAdapter()

    fun setAppInfo(appInfo: List<AppModel>)

    fun showProgress()

    fun hideProgress()

    fun obtainSuccess()

    fun obtainError()
}
