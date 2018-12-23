package sample.util.develop.android.image.select.crop

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import com.yalantis.ucrop.UCrop
import sample.util.develop.android.image.select.R
import java.io.File
import java.io.FileNotFoundException

class CropManager {

    private lateinit var cropCallBack: CropCallBack
    private lateinit var imagePath: Uri

    companion object {

        private const val TYPE_CAMERA = 1111
        private const val TYPE_ALBUM = 1112
        private const val TYPE_KITKAT_ALBUM = 1113

        private const val IMAGE_TYPE = "image/jpeg"
        private const val IMAGE_TYPE_All = "image/*"

        val instance by lazy { CropManager() }
    }

    fun init(cropCallBack: CropCallBack) {
        this.cropCallBack = cropCallBack
    }

    /**
     * 打开相机
     */
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        imagePath = Uri.fromFile(getDiskCacheDir(cropCallBack.cropActivity))


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath)
            cropCallBack
                .cropActivity
                .startActivityForResult(intent, TYPE_CAMERA)

        } else {

            val contentValues = ContentValues(2)
            contentValues.put(MediaStore.Images.Media.DATA, imagePath.path)
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, IMAGE_TYPE)
            val uri = cropCallBack.cropActivity.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            cropCallBack
                .cropActivity
                .startActivityForResult(intent, TYPE_CAMERA)

        }
    }

    /**
     * 打开图库
     */
    fun openAlbum() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            cropCallBack
                .cropActivity
                .startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).setType(IMAGE_TYPE), TYPE_ALBUM)
        } else {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = IMAGE_TYPE_All
            cropCallBack
                .cropActivity
                .startActivityForResult(intent, TYPE_KITKAT_ALBUM)
        }
    }

    /**
     * 接管 选择回调
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_CANCELED) {

            cropCallBack.onCropCancel()

        } else if (resultCode == UCrop.RESULT_ERROR) {

            if (data != null) {
                val cropError = UCrop.getError(data)
                if (cropError != null) {
                    cropCallBack.onCropError(cropError.message ?: "")
                } else {
                    cropCallBack.onCropError("裁剪出现未知错误")
                }
            } else {
                cropCallBack.onCropError("获取相册图片出现错误")
            }

        } else if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TYPE_CAMERA -> {
                    notifyImageToCamera(cropCallBack.cropActivity, imagePath)
                    UCrop.of(
                        imagePath,
                        Uri.fromFile(getDiskCacheDir(cropCallBack.cropActivity))
                    )
                        .withAspectRatio(16f, 9f)
                        .withOptions(getOptions(cropCallBack.cropActivity))
                        .start(cropCallBack.cropActivity)
                }
                TYPE_ALBUM ->
                    if (data != null) {
                        UCrop.of(data.data, Uri.fromFile(getDiskCacheDir(cropCallBack.cropActivity)))
                            .withAspectRatio(16f, 9f)
                            .withOptions(getOptions(cropCallBack.cropActivity))
                            .start(cropCallBack.cropActivity)
                    } else {
                        cropCallBack.onCropError("获取相册图片出现错误")
                    }
                TYPE_KITKAT_ALBUM ->

                    if (data != null) {
                        UCrop.of(
                            data.data,
                            Uri.fromFile(getDiskCacheDir(cropCallBack.cropActivity))
                        )
                            .withAspectRatio(16f, 9f)
                            .withOptions(getOptions(cropCallBack.cropActivity))
                            .start(cropCallBack.cropActivity)
                    } else {
                        cropCallBack.onCropError("获取相册图片出现错误")
                    }
                UCrop.REQUEST_CROP -> cropCallBack.onCropSuccess(UCrop.getOutput(data ?: Intent()))
            }
        }
    }

    private fun getDiskCacheDir(context: Context): File {
        val cachePath: String =
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
                context.externalCacheDir.path
            } else {
                context.cacheDir.path
            }
        return File(cachePath, imageName())
    }

    /**
     * 简单的设置 UCrop
     */
    private fun getOptions(activity: Activity): UCrop.Options {
        val options = UCrop.Options()
        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.colorPrimary))
        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.colorPrimaryDark))
        return options
    }

    /**
     * 更新系统图库
     */
    private fun notifyImageToCamera(context: Context, uri: Uri) {
        try {
            val file = File(uri.path)
            MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, file.name, null)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
    }

    private fun imageName(): String {
        return System.currentTimeMillis().toString() + ".jpg"
    }
}
