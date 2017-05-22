package scan;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * by y on 2017/2/15
 */

class QueryApp {


    static List<AppModel> getAppInfo(Context context, int type, boolean isNoSystem) {
        List<AppModel> appInfo = new ArrayList<>();
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(list, new ApplicationInfo.DisplayNameComparator(packageManager));
        appInfo.clear();
        switch (type) {
            case ScanAppUtils.ALL_APP:
                for (ApplicationInfo applicationInfo : list) {
                    appInfo.add(getAppInfo(applicationInfo, packageManager));
                }
                break;
            case ScanAppUtils.SYSTEM_APP:
                for (ApplicationInfo applicationInfo : list) {
                    if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        appInfo.add(getAppInfo(applicationInfo, packageManager));
                    }
                }
                break;
            case ScanAppUtils.NO_SYSTEM_APP:
                for (ApplicationInfo applicationInfo : list) {
                    //非系统程序
                    if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                        appInfo.add(getAppInfo(applicationInfo, packageManager));
                    }//更新后，成为第三方应用
                    else if ((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0 && isNoSystem) {
                        appInfo.add(getAppInfo(applicationInfo, packageManager));
                    }
                }
                break;
        }
        return appInfo;
    }

    private static AppModel getAppInfo(ApplicationInfo applicationInfo, PackageManager pm) {
        AppModel appModel = new AppModel();
        appModel.setAppLabel((String) applicationInfo.loadLabel(pm));
        appModel.setAppIcon(applicationInfo.loadIcon(pm));
        appModel.setPkgName(applicationInfo.packageName);
        return appModel;
    }
}
