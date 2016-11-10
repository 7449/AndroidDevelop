package framework.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

public class SpfUtils {

    private static SharedPreferences sharedPreferences;
    private static final String SHAREDPREFERENCES_NAME = "example";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static void initSharePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        initSharePreferences(context);
    }

    public static boolean isNull() {
        return sharedPreferences == null;
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

    public static void clearAll() {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().clear().apply();
    }

}
