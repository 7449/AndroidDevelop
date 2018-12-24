package scan.listener

import android.content.Context

import scan.AppModel

/**
 * by y on 2017/2/15
 */

interface ScanListener {
    val scanContext: Context

    fun onScanStart()

    fun onScanSuccess(data: List<AppModel>)

    fun onScanError(e: Throwable)
}
