package com.greendao.multitable;


import com.greendao.multitable.bean.test.DaoMaster;
import com.greendao.multitable.bean.test.DaoSession;

/**
 * by y on 05/07/2017.
 */

public class DBManager {

    private static final String SQL_NAME = "sample";

    private DBManager() {
    }

    public static DaoSession getDaoSession() {
        return SessionHolder.daoSession;
    }

    private static class SessionHolder {
        private static final DaoSession daoSession = new DaoMaster(new DaoMaster.DevOpenHelper(App.getContext(), SQL_NAME, null).getWritableDatabase()).newSession();
    }
}
