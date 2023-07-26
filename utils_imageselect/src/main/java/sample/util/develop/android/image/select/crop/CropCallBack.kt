package sample.util.develop.android.image.select.crop

import android.app.Activity
import android.net.Uri

interface CropCallBack {
    val cropActivity: Activity

    fun onCropCancel()

    fun onCropSuccess(uri: Uri?)

    fun onCropError(errorMessage: String?)
}
