package com.fuckapp.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fuckapp.fragment.model.AppModel;
import com.fuckapp.main.App;

import java.io.DataOutputStream;
import java.util.List;

/**
 * by y on 2016/10/20.
 */

public class RootUtils {
    public static final String ADB_COMMAND_HIDE = "pm hide ";
    public static final String ADB_COMMAND_UN_HIDE = "pm unhide ";

    public static void execShell(List<AppModel> appModels, RootUtilsInterface rootUtilsInterface) {
        for (AppModel appModel : appModels) {
            execShell(ADB_COMMAND_HIDE + appModel.getPkgName());
        }
        rootUtilsInterface.execShellSuccess();
    }

    public interface RootUtilsInterface {
        void execShellSuccess();
    }

    public static boolean execShell(String adbCommand) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(adbCommand + "\n");
            os.writeBytes("exit\n");
            os.flush();
            return process.waitFor() == 0;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * hide之后的app是false
     */
    public static boolean isApk(String packageName) {
        final PackageManager packageManager = App.getApp().getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (PackageInfo packageInfo : pinfo) {
                if (packageInfo.packageName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
