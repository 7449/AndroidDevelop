package com.rn.netdetail.db;

import android.annotation.SuppressLint;
import android.content.Context;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ObjectBoxUtils {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    private ObjectBoxUtils() {
    }

    public static BoxStore get() {
        return ObjectBoxHolder.BOX_STORE_BUILDER;
    }

    private static class ObjectBoxHolder {
        private static final BoxStore BOX_STORE_BUILDER = context == null ? null :
                MyObjectBox
                        .builder().
                        androidContext(context).
                        build();
    }

    public static Box<ObjectBoxNetEntity> getListBox() {
        return get().boxFor(ObjectBoxNetEntity.class);
    }
}
