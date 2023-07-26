package sample.util.develop.android.error.library

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * by y on 05/12/2017.
 */

object FileUtils {

    /**
     * 获取文件下载路径
     *
     * @param fileName 目录名称
     * @return 路径
     */
    fun getDiskFileDir(context: Context, fileName: String): File {
        val path =
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
                val externalFilesDir = context.getExternalFilesDir(fileName)
                externalFilesDir?.path ?: ""
            } else {
                context.cacheDir.path
            }
        return File(path)
    }
}
