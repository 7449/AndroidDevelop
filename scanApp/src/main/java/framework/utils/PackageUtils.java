package framework.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;

import com.lock.main.Constant;

import framework.App;

/**
 * by y on 2017/2/15
 */

public class PackageUtils {
    private static PackageManager pm = App.getContext().getPackageManager();

    /**
     * 获取图标
     */
    public static Drawable getAppIcon(String packageName) {
        try {
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 打开设置页面该app的详细信息页面
     */
    public static void startSettingApp(Activity activity, String packageName) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts(Constant.START_SETTING_SCHEME, packageName, null);
        intent.setData(uri);
        activity.startActivity(intent);
    }

    /**
     * 启动app
     */
    public static boolean startApp(Activity activity, String packageName) {
        try {
            Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
