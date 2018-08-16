package com.status.layout

import android.view.View

/**
 * by y on 14/07/2017.
 */

interface OnStatusClickListener {
    fun onNorMalClick(view: View)

    fun onLoadingClick(view: View)

    fun onEmptyClick(view: View)

    fun onSuccessClick(view: View)

    fun onErrorClick(view: View)
}
