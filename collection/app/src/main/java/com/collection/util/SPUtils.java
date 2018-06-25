package com.collection.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * by y on 02/08/2017.
 */

public class SPUtils {
    private static final String SP_NAME = "SP";
    private static SharedPreferences sharedPreferences;


    private SPUtils() {
    }

    private static void initSharePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        initSharePreferences(context.getApplicationContext());
    }

    public static boolean isNull() {
        return sharedPreferences == null;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, boolean value) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static String getString(String key, String defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void setString(String key, String value) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static long getLong(String key, Long defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void setInt(String key, int value) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static void setLong(String key, Long value) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public static void clearAll() {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().clear().apply();
    }

    public static void clear(String key) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().remove(key).apply();
    }

    public static boolean containsValue(Object value) {
        return sharedPreferences.getAll().containsValue(value);
    }
}
