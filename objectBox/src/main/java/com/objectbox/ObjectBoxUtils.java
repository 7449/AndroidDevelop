package com.objectbox;

import io.objectbox.BoxStore;

/**
 * by y on 28/09/2017.
 */

public class ObjectBoxUtils {

    private ObjectBoxUtils() {
    }


    public static BoxStore getBoxStore() {
        return ObjectBoxStoreHolder.BOX_STORE_BUILDER;
    }

    public static DaoSession getDao() {
        return ObjectBoxDaoHolder.DAO_SESSION;
    }


    private static class ObjectBoxStoreHolder {
        private static final BoxStore BOX_STORE_BUILDER = MyObjectBox.builder().androidContext(App.getContext()).build();
    }

    private static class ObjectBoxDaoHolder {
        private static final DaoSession DAO_SESSION = new DaoSession(ObjectBoxStoreHolder.BOX_STORE_BUILDER);
    }

}
