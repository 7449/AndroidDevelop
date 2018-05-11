package com.statusbar.status;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * by y on 2017/4/26.
 */

@TargetApi(Build.VERSION_CODES.KITKAT)
public class StatusBarUtils {

    private static final String TAG = "StatusBarUtils";

    private Activity activity = null;


    private StatusBarUtils(Activity activity) {
        if (activity == null) {
            throw new NullPointerException("please check activity...,make sure the activity is not null");
        }
        this.activity = activity;
    }


    public static StatusBarUtils get(@NonNull Activity activity) {
        return new StatusBarUtils(activity);
    }


    /**
     * 反射处理状态栏颜色
     *
     * @param color 需要改变的颜色
     */
    public void reflectAlterStatusBarColor(@ColorInt int color) {
        try {
            Window window = activity.getWindow();
            window.getClass().getDeclaredMethod("setStatusBarColor", int.class).invoke(window, color);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            Log.i(TAG, e.toString());
        }
    }

    /**
     * 系统提供方法处理状态栏颜色
     *
     * @param color 需要改变的颜色
     */
    public void alterStatusBarColor(@ColorInt int color) {
        if (Utils.isLollipop()) {
            activity.getWindow().setStatusBarColor(color);
        }
    }

    /**
     * 修改状态栏颜色
     * <p>
     * <p>
     * <item name="android:windowLightStatusBar">true</item>
     *
     * @param dark true 已设置状态栏深色主题 false 已设置状态栏浅色主题
     */
    public void alterStatusBarTextColor(boolean dark) {
        if (Utils.isM()) {
            Window window = activity.getWindow();
            if (dark) {
                window.clearFlags(View.SYSTEM_UI_FLAG_VISIBLE);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                window.clearFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 设置MIUI状态栏状态
     *
     * @param dark true 已设置状态栏深色主题 false 已设置状态栏浅色主题
     * @return true 设置成功
     */
    public boolean miuiSetStatusBarLightMode(boolean dark) {
        boolean result = false;
        try {
            Window window = activity.getWindow();
            int darkModeFlag;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method method = window.getClass().getMethod("setExtraFlags", int.class, int.class);
            if (dark) {
                method.invoke(window, darkModeFlag, darkModeFlag);
            } else {
                method.invoke(window, 0, darkModeFlag);
            }
            result = true;
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
        return result;
    }

    /*************************** Flyme **********************************/

    /**
     * 设置Flyme状态栏字体颜色
     *
     * @param color 颜色
     */
    public void setStatusBarDarkIcon(int color) {
        try {
            Method setStatusBarDarkIcon = Activity.class.getMethod("setStatusBarDarkIcon", int.class);
            Field statusBarColor = WindowManager.LayoutParams.class.getField("statusBarColor");
            if (setStatusBarDarkIcon != null) {
                setStatusBarDarkIcon.invoke(activity, color);
            } else {
                boolean whiteColor = Utils.isBlackColor(color, 50);
                if (statusBarColor != null) {
                    setStatusBarDarkIcon(whiteColor, whiteColor);
                    setNoActivityStatusBarDarkIcon(color);
                } else {
                    setStatusBarDarkIcon(whiteColor);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置状态栏字体颜色(只限全屏非activity情况)
     *
     * @param color 颜色
     */
    public void setNoActivityStatusBarDarkIcon(int color) {
        try {
            setStatusBarColor(activity.getWindow(), color);
            if (Utils.isLollipop_Mr1()) {
                setStatusBarDarkIcon(activity.getWindow().getDecorView(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置状态栏字体图标颜色
     *
     * @param dark 是否深色 true为深色 false 为白色
     */
    public void setStatusBarDarkIcon(boolean dark) {
        setStatusBarDarkIcon(dark, true);
    }


    /**
     * 设置状态栏颜色
     */
    public void setStatusBarDarkIcon(View view, boolean dark) {
        try {
            Field field = View.class.getField("SYSTEM_UI_FLAG_LIGHT_STATUS_BAR");
            int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = field.getInt(null);
            int oldVis = view.getSystemUiVisibility();
            int newVis = oldVis;
            if (dark) {
                newVis |= SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                newVis &= ~SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            if (newVis != oldVis) {
                view.setSystemUiVisibility(newVis);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置状态栏颜色
     */
    public void setStatusBarColor(Window window, int color) {
        WindowManager.LayoutParams winParams = window.getAttributes();
        try {
            Field statusBarColor = WindowManager.LayoutParams.class.getField("statusBarColor");
            if (statusBarColor != null) {
                int oldColor = statusBarColor.getInt(winParams);
                if (oldColor != color) {
                    statusBarColor.set(winParams, color);
                    window.setAttributes(winParams);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置状态栏字体图标颜色(只限全屏非activity情况)
     *
     * @param window 当前窗口
     * @param dark   是否深色 true为深色 false 为白色
     */
    public void setStatusBarDarkIcon(Window window, boolean dark) {
        if (!Utils.isM()) {
            Utils.changeMeizuFlag(window.getAttributes(), "MEIZU_FLAG_DARK_STATUS_BAR_ICON", dark);
        } else {
            View decorView = window.getDecorView();
            if (decorView != null) {
                setStatusBarDarkIcon(decorView, dark);
                setStatusBarColor(window, 0);
            }
        }
    }

    public void setStatusBarDarkIcon(boolean dark, boolean flag) {
        try {
            Method setStatusBarDarkIcon = Activity.class.getMethod("setStatusBarDarkIcon", boolean.class);
            if (setStatusBarDarkIcon != null) {
                setStatusBarDarkIcon.invoke(activity, dark);
            } else {
                if (flag) {
                    setStatusBarDarkIcon(activity.getWindow(), dark);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private static class Utils {

        /**
         * 判断颜色是否偏黑色
         *
         * @param color 颜色
         * @param level 级别
         */
        static boolean isBlackColor(int color, int level) {
            int grey = toGrey(color);
            return grey < level;
        }

        /**
         * 颜色转换成灰度值
         *
         * @param rgb 颜色
         */
        static int toGrey(int rgb) {
            int blue = rgb & 0x000000FF;
            int green = (rgb & 0x0000FF00) >> 8;
            int red = (rgb & 0x00FF0000) >> 16;
            return (red * 38 + green * 75 + blue * 15) >> 7;
        }


        static boolean isLollipop() {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        }

        static boolean isLollipop_Mr1() {
            return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
        }

        static boolean isM() {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        }


        static boolean changeMeizuFlag(WindowManager.LayoutParams winParams, String flagName, boolean on) {
            try {
                Field f = winParams.getClass().getDeclaredField(flagName);
                f.setAccessible(true);
                int bits = f.getInt(winParams);
                Field f2 = winParams.getClass().getDeclaredField("meizuFlags");
                f2.setAccessible(true);
                int meizuFlags = f2.getInt(winParams);
                int oldFlags = meizuFlags;
                if (on) {
                    meizuFlags |= bits;
                } else {
                    meizuFlags &= ~bits;
                }
                if (oldFlags != meizuFlags) {
                    f2.setInt(winParams, meizuFlags);
                    return true;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
