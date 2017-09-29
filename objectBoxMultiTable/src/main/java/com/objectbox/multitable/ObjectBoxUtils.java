package com.objectbox.multitable;

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


    private static class ObjectBoxStoreHolder {
        private static final BoxStore BOX_STORE_BUILDER = MyObjectBox.builder().androidContext(App.getContext()).build();
    }
}
