package com.fuckapp.utils

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.fuckapp.fragment.model.AppModel
import com.fuckapp.main.App
import java.util.Collections


/**
 * by y on 2016/10/20.
 */

object QueryAppUtils {

    private lateinit var pm: PackageManager

    fun getAppInfo(type: Int): List<AppModel> {
        val appInfo = ArrayList<AppModel>()
        pm = App.instance.packageManager
        val list = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES)
        Collections.sort(list, ApplicationInfo.DisplayNameComparator(pm))
        appInfo.clear()
        when (type) {
            Constant.ALL_APP -> for (applicationInfo in list) {
                appInfo.add(getAppInfo(applicationInfo))
            }

            Constant.SYSTEM_APP -> for (applicationInfo in list) {
                if (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                    appInfo.add(getAppInfo(applicationInfo))
                }
            }

            Constant.NO_SYSTEM_APP -> for (applicationInfo in list) {
                //非系统程序
                if (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM <= 0) {
                    appInfo.add(getAppInfo(applicationInfo))
                }//更新后，成为第三方应用
                else if (applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0) {
                    appInfo.add(getAppInfo(applicationInfo))
                }
            }

            Constant.HIDE_APP -> for (applicationInfo in list) {
                if (!RootUtils.isApk(applicationInfo.packageName)) {
                    appInfo.add(getAppInfo(applicationInfo))
                }
            }
        }
        return appInfo
    }

    private fun getAppInfo(applicationInfo: ApplicationInfo): AppModel {
        val appModel = AppModel()
        appModel.appLabel = applicationInfo.loadLabel(pm) as String
        appModel.appIcon = applicationInfo.loadIcon(pm)
        appModel.pkgName = applicationInfo.packageName
        return appModel
    }

}
