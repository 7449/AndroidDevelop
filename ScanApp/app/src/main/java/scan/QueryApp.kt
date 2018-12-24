package scan

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import java.util.*


/**
 * by y on 2017/2/15
 */

object QueryApp {

    fun getAppInfo(context: Context, type: Int, isNoSystem: Boolean): List<AppModel> {
        val appInfo = ArrayList<AppModel>()
        val packageManager = context.applicationContext.packageManager
        val list = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES)
        Collections.sort(list, ApplicationInfo.DisplayNameComparator(packageManager))
        appInfo.clear()
        when (type) {
            ScanAppUtils.ALL_APP -> for (applicationInfo in list) {
                appInfo.add(getAppInfo(applicationInfo, packageManager))
            }
            ScanAppUtils.SYSTEM_APP -> for (applicationInfo in list) {
                if (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                    appInfo.add(getAppInfo(applicationInfo, packageManager))
                }
            }
            ScanAppUtils.NO_SYSTEM_APP -> for (applicationInfo in list) {
                //非系统程序
                if (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM <= 0) {
                    appInfo.add(getAppInfo(applicationInfo, packageManager))
                }//更新后，成为第三方应用
                else if (applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0 && isNoSystem) {
                    appInfo.add(getAppInfo(applicationInfo, packageManager))
                }
            }
        }
        return appInfo
    }

    private fun getAppInfo(applicationInfo: ApplicationInfo, pm: PackageManager): AppModel {
        val appModel = AppModel()
        appModel.appLabel = applicationInfo.loadLabel(pm).toString()
        appModel.appIcon = applicationInfo.loadIcon(pm)
        appModel.pkgName = applicationInfo.packageName
        return appModel
    }
}
