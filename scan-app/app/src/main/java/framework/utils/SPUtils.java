package framework.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import com.lock.main.model.MainBean;

public class SPUtils {

    private static SharedPreferences sharedPreferences;
    private static final String SHAREDPREFERENCES_NAME = "look";
    public static final String USER_NAME = "user_name";
    public static final String PASS_WORD = "pass_word";
    public static final String HEADER_URL = "header";
    public static final String REMEMBER_PASSWORD = "remember_password";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static void initSharePreferences(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
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

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, Boolean value) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static Integer getInt(String key, Integer defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void setInt(String key, Integer value) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public static Long getLong(String key, Long defaultValue) {
        if (isNull()) {
            return defaultValue;
        }
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static void setLong(String key, Long value) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().putLong(key, value).apply();
    }


    public static void clear(String key) {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().remove(key).apply();
    }

    public static void clearAll() {
        if (isNull()) {
            return;
        }
        sharedPreferences.edit().clear().apply();
    }

    public static void writeUser(MainBean userBean) {
        if (isNull()) {
            return;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(USER_NAME, userBean.getUserName()).apply();
        edit.putString(PASS_WORD, userBean.getPassWord());
        edit.apply();
    }

    public static MainBean readeUser() {
        MainBean userBean = new MainBean();
        userBean.setUserName(sharedPreferences.getString(USER_NAME, ""));
        userBean.setPassWord(sharedPreferences.getString(PASS_WORD, ""));
        return userBean;
    }

    public static boolean isLogin() {
        MainBean userBean = readeUser();
        return TextUtils.isEmpty(userBean.getUserName()) && TextUtils.isEmpty(userBean.getPassWord());
    }


    public static String getUserName() {
        return readeUser().getUserName();
    }

    public static String getPassWord() {
        return readeUser().getPassWord();
    }

    public static void updatePassWord(String newPassWord) {
        setString(PASS_WORD, newPassWord);
    }
}
