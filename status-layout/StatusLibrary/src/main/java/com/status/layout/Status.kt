package com.status.layout

import android.support.annotation.StringDef

/**
 * @author y
 */
object Status {
    const val NORMAL = "StatusLayout:Normal"
    const val LOADING = "StatusLayout:Loading"
    const val EMPTY = "StatusLayout:Empty"
    const val SUCCESS = "StatusLayout:Success"
    const val ERROR = "StatusLayout:Error"

    @StringDef(Status.NORMAL, Status.LOADING, Status.EMPTY, Status.SUCCESS, Status.ERROR)
    @Retention(AnnotationRetention.SOURCE)
    annotation class StatusAnnotation
}
