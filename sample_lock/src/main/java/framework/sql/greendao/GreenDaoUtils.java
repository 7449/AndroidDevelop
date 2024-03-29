package framework.sql.greendao;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;

import framework.App;
import framework.sql.DaoMaster;
import framework.sql.DaoSession;

/**
 * by y on 2017/2/15
 */

public class GreenDaoUtils {
    private static final String SQL_NAME = "app_normal";
    @SuppressLint("StaticFieldLeak")
    private static DaoMaster.DevOpenHelper devOpenHelper;
    private static SQLiteDatabase sqLiteDatabase;
    private static DaoMaster daoMaster;

    public static DaoSession getInstance() {
        return SessionHolder.daoSession;
    }

    private static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(getSQLiteDatabase());
        }
        return daoMaster;
    }

    private static SQLiteDatabase getSQLiteDatabase() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = getDevOpenHelper().getWritableDatabase();
        }
        return sqLiteDatabase;
    }

    private static DaoMaster.DevOpenHelper getDevOpenHelper() {
        if (devOpenHelper == null) {
            devOpenHelper = new DaoMaster.DevOpenHelper(App.Companion.getInstance(), SQL_NAME);
        }
        return devOpenHelper;
    }

    private static class SessionHolder {
        static final DaoSession daoSession = getDaoMaster().newSession();
    }

}
