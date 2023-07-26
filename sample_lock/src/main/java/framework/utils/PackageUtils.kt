package framework.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.Settings
import com.lock.main.Constant
import framework.App

/**
 * by y on 2017/2/15
 */

object PackageUtils {
    private val pm = App.instance.packageManager

    /**
     * 获取图标
     */
    fun getAppIcon(packageName: String): Drawable? {
        try {
            val info = pm.getApplicationInfo(packageName, 0)
            return info.loadIcon(pm)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return null
    }


    /**
     * 打开设置页面该app的详细信息页面
     */
    fun startSettingApp(activity: Activity, packageName: String) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts(Constant.START_SETTING_SCHEME, packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }

    /**
     * 启动app
     */
    fun startApp(activity: Activity, packageName: String): Boolean {
        return try {
            val intent = activity.packageManager.getLaunchIntentForPackage(packageName)
            activity.startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }

    }

}
