package framework.sql.greendao;

import java.util.List;

import framework.sql.NormalBean;
import framework.sql.NormalBeanDao;

/**
 * by y on 2017/2/15
 */

public class GreenDaoDbUtils {

    private static NormalBeanDao getNormalDao() {
        return GreenDaoUtils.getInstance().getNormalBeanDao();
    }

    public static List<NormalBean> getNormalAll() {
        return getNormalDao().loadAll();
    }


    private static boolean isNormalEmpty(String key) {
        return getNormalDao().queryBuilder().where(NormalBeanDao.Properties.PkgName.eq(key)).unique() == null;
    }

    public static void clearNormal() {
        getNormalDao().deleteAll();
    }

    public static void clearNormal(Long key) {
        getNormalDao().deleteByKey(key);
    }

    public static void NormalInsert(String packageName, String appLabel) {
        if (isNormalEmpty(packageName)) {
            getNormalDao().insert(new NormalBean(null, packageName, appLabel));
        }
    }

}
