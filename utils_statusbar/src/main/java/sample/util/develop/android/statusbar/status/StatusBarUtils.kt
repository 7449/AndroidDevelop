package sample.util.develop.android.statusbar.status

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import java.lang.reflect.InvocationTargetException

class StatusBarUtils private constructor(private val activity: Activity) {

    companion object {
        private const val TAG = "StatusBarUtils"

        operator fun get(activity: Activity): StatusBarUtils {
            return StatusBarUtils(activity)
        }
    }


    /**
     * 反射处理状态栏颜色
     *
     * @param color 需要改变的颜色
     */
    fun reflectAlterStatusBarColor(@ColorInt color: Int) {
        try {
            val window = activity.window
            window.javaClass.getDeclaredMethod("setStatusBarColor", Int::class.javaPrimitiveType)
                .invoke(window, color)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            Log.i(TAG, e.toString())
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            Log.i(TAG, e.toString())
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            Log.i(TAG, e.toString())
        }

    }

    /**
     * 系统提供方法处理状态栏颜色
     *
     * @param color 需要改变的颜色
     */
    fun alterStatusBarColor(@ColorInt color: Int) {
        if (Utils.isLollipop) {
            activity.window.statusBarColor = color
        }
    }

    /**
     * 修改状态栏颜色
     *
     *
     *
     *
     * <item name="android:windowLightStatusBar">true</item>
     *
     * @param dark true 已设置状态栏深色主题 false 已设置状态栏浅色主题
     */
    fun alterStatusBarTextColor(dark: Boolean) {
        if (Utils.isM) {
            val window = activity.window
            if (dark) {
                window.clearFlags(View.SYSTEM_UI_FLAG_VISIBLE)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                window.clearFlags(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }
    }

    /**
     * 设置MIUI状态栏状态
     *
     * @param dark true 已设置状态栏深色主题 false 已设置状态栏浅色主题
     * @return true 设置成功
     */
    @SuppressLint("PrivateApi")
    fun miuiSetStatusBarLightMode(dark: Boolean): Boolean {
        var result = false
        try {
            val window = activity.window
            val darkModeFlag: Int
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val method =
                window.javaClass.getMethod(
                    "setExtraFlags",
                    Int::class.javaPrimitiveType,
                    Int::class.javaPrimitiveType
                )
            if (dark) {
                method.invoke(window, darkModeFlag, darkModeFlag)
            } else {
                method.invoke(window, 0, darkModeFlag)
            }
            result = true
        } catch (e: Exception) {
            Log.i(TAG, e.toString())
        }

        return result
    }

    /*************************** Flyme **********************************/

    /**
     * 设置Flyme状态栏字体颜色
     *
     * @param color 颜色
     */
    fun setStatusBarDarkIcon(color: Int) {
        try {
            val method =
                Activity::class.java.getMethod("setStatusBarDarkIcon", Int::class.javaPrimitiveType)
            method.invoke(activity, color)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

    }

    /**
     * 设置状态栏字体颜色(只限全屏非activity情况)
     *
     * @param color 颜色
     */
    fun setNoActivityStatusBarDarkIcon(color: Int) {
        try {
            setStatusBarColor(activity.window, color)
            if (Utils.isLollipop_Mr1) {
                setStatusBarDarkIcon(activity.window.decorView, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 设置状态栏颜色
     */
    fun setStatusBarDarkIcon(view: View, dark: Boolean) {
        try {
            val field = View::class.java.getField("SYSTEM_UI_FLAG_LIGHT_STATUS_BAR")
            val SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = field.getInt(null)
            val oldVis = view.systemUiVisibility
            var newVis = oldVis
            newVis = if (dark) {
                newVis or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                newVis and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            if (newVis != oldVis) {
                view.systemUiVisibility = newVis
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    /**
     * 设置状态栏颜色
     */
    fun setStatusBarColor(window: Window, color: Int) {
        val winParams = window.attributes
        try {
            val statusBarColor = WindowManager.LayoutParams::class.java.getField("statusBarColor")
            val oldColor = statusBarColor.getInt(winParams)
            if (oldColor != color) {
                statusBarColor.set(winParams, color)
                window.attributes = winParams
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }

    /**
     * 设置状态栏字体图标颜色(只限全屏非activity情况)
     *
     * @param window 当前窗口
     * @param dark   是否深色 true为深色 false 为白色
     */
    fun setStatusBarDarkIcon(window: Window, dark: Boolean) {
        if (!Utils.isM) {
            Utils.changeMeizuFlag(window.attributes, "MEIZU_FLAG_DARK_STATUS_BAR_ICON", dark)
        } else {
            val decorView = window.decorView
            setStatusBarDarkIcon(decorView, dark)
            setStatusBarColor(window, 0)
        }
    }

    /**
     * 设置状态栏字体图标颜色
     *
     * @param dark 是否深色 true为深色 false 为白色
     */
    fun setStatusBarDarkIcon(dark: Boolean) {
        try {
            val method = Activity::class.java.getMethod(
                "setStatusBarDarkIcon",
                Boolean::class.javaPrimitiveType
            )
            method.invoke(activity, dark)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }


    private object Utils {

        val isLollipop: Boolean
            get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

        val isLollipop_Mr1: Boolean
            get() = Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1

        val isM: Boolean
            get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

        /**
         * 判断颜色是否偏黑色
         *
         * @param color 颜色
         * @param level 级别
         */
        fun isBlackColor(color: Int, level: Int): Boolean {
            val grey = toGrey(color)
            return grey < level
        }

        /**
         * 颜色转换成灰度值
         *
         * @param rgb 颜色
         */
        fun toGrey(rgb: Int): Int {
            val blue = rgb and 0x000000FF
            val green = rgb and 0x0000FF00 shr 8
            val red = rgb and 0x00FF0000 shr 16
            return red * 38 + green * 75 + blue * 15 shr 7
        }


        fun changeMeizuFlag(
            winParams: WindowManager.LayoutParams,
            flagName: String,
            on: Boolean
        ): Boolean {
            try {
                val f = winParams.javaClass.getDeclaredField(flagName)
                f.isAccessible = true
                val bits = f.getInt(winParams)
                val f2 = winParams.javaClass.getDeclaredField("meizuFlags")
                f2.isAccessible = true
                var meizuFlags = f2.getInt(winParams)
                val oldFlags = meizuFlags
                meizuFlags = if (on) {
                    meizuFlags or bits
                } else {
                    meizuFlags and bits.inv()
                }
                if (oldFlags != meizuFlags) {
                    f2.setInt(winParams, meizuFlags)
                    return true
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }

            return false
        }
    }
}
