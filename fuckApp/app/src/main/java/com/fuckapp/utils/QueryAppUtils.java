package com.fuckapp.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.fuckapp.fragment.model.AppModel;
import com.fuckapp.main.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fuckapp.utils.Constant.ALL_APP;
import static com.fuckapp.utils.Constant.HIDE_APP;
import static com.fuckapp.utils.Constant.NO_SYSTEM_APP;
import static com.fuckapp.utils.Constant.SYSTEM_APP;

/**
 * by y on 2016/10/20.
 */

public class QueryAppUtils {

    private static PackageManager pm;

    public static List<AppModel> getAppInfo(int type) {
        List<AppModel> appInfo = new ArrayList<>();
        pm = App.getApp().getPackageManager();
        List<ApplicationInfo> list = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(list, new ApplicationInfo.DisplayNameComparator(pm));
        appInfo.clear();
        switch (type) {
            case ALL_APP:
                for (ApplicationInfo applicationInfo : list) {
                    appInfo.add(getAppInfo(applicationInfo));
                }
                break;
            case SYSTEM_APP:
                for (ApplicationInfo applicationInfo : list) {
                    if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        appInfo.add(getAppInfo(applicationInfo));
                    }
                }
                break;
            case NO_SYSTEM_APP:
                for (ApplicationInfo applicationInfo : list) {
                    //非系统程序
                    if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                        appInfo.add(getAppInfo(applicationInfo));
                    }//更新后，成为第三方应用
                    else if ((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                        appInfo.add(getAppInfo(applicationInfo));
                    }
                }
                break;
            case HIDE_APP:
                for (ApplicationInfo applicationInfo : list) {
                    if (!RootUtils.isApk(applicationInfo.packageName)) {
                        appInfo.add(getAppInfo(applicationInfo));
                    }
                }
                break;
        }
        return appInfo;
    }

    private static AppModel getAppInfo(ApplicationInfo applicationInfo) {
        AppModel appModel = new AppModel();
        appModel.setAppLabel((String) applicationInfo.loadLabel(pm));
        appModel.setAppIcon(applicationInfo.loadIcon(pm));
        appModel.setPkgName(applicationInfo.packageName);
        return appModel;
    }

}
