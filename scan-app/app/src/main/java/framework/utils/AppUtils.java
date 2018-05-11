package framework.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.lock.R;
import com.lock.main.Constant;

import java.util.List;

import framework.sql.NormalBean;
import framework.sql.greendao.GreenDaoDbUtils;
import scan.AppModel;

/**
 * by y on 2017/2/15
 */

public class AppUtils {

    public static List<AppModel> getList(List<AppModel> list) {
        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.equals(Constant.APP_PACKAGE_NAME, list.get(i).getPkgName())) {
                list.remove(i);
            }
        }
        return list;
    }

    public static void insertDB(List<AppModel> tempList) {
        for (AppModel appModel : tempList) {
            GreenDaoDbUtils.NormalInsert(appModel.getPkgName(), appModel.getAppLabel());
        }
    }

    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static List<AppModel> copyList(List<NormalBean> oldList, List<AppModel> newList) {
        for (NormalBean n : oldList) {
            newList.add(new AppModel(n.getAppLabel(), PackageUtils.getAppIcon(n.getPkgName()), n.getPkgName()));
        }
        return newList;
    }

    public static int getColor(boolean isColor) {
        return isColor ? R.color.colorWhite : R.color.colorGray;
    }

    public static boolean isEquals(String packageName) {
        for (NormalBean normalBean : GreenDaoDbUtils.getNormalAll()) {
            if (TextUtils.equals(normalBean.getPkgName(), packageName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean openPick(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(intent, Activity.RESULT_CANCELED);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
