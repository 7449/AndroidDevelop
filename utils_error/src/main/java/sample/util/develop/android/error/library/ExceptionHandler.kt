package sample.util.develop.android.error.library

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.widget.Toast
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * by y.
 *
 *
 * Description:
 */
class ExceptionHandler : Thread.UncaughtExceptionHandler {

    companion object {

        @SuppressLint("StaticFieldLeak")
        val instance = ExceptionHandler()

        @SuppressLint("SimpleDateFormat")
        private val currentTime: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
    }

    private lateinit var context: Context
    private lateinit var defaultHandler: Thread.UncaughtExceptionHandler
    private val devInfo = HashMap<String, String>()
    private val df = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())

    fun setCustomCrashHandler(ctx: Context) {
        context = ctx
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * @param thread 发生异常的线程
     * @param ex     抛出的异常
     * @return void
     * @name uncaughtException(Thread thread, Throwable ex)
     * @description 当发生UncaughtException时会回调此函数
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        val isDone = doException(ex)
        if (!isDone) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(thread, ex)
        } else {
            // 如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app
            try {
                Thread.sleep(3000)
            } catch (ignored: InterruptedException) {

            }

        }
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }

    /**
     * @param ex 抛出的异常
     * @return 异常处理标志
     */
    private fun doException(ex: Throwable?): Boolean {
        if (ex == null) {
            return true
        }
        object : Thread() {
            override fun run() {
                Looper.prepare()
                Toast.makeText(context, "程序出现错误退出！", Toast.LENGTH_LONG).show()
                Looper.loop()
            }
        }.start()
        collectDeviceInfo(context)
        saveExceptionToFile(ex)
        return true
    }


    /**
     * @param ctx
     * @return void
     * @name collectDeviceInfo(Context ctx)
     * @description 收集必须的设备信息
     */
    private fun collectDeviceInfo(ctx: Context) {
        try {
            val pm = ctx.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                devInfo["versionName"] = pi.versionName
                devInfo["versionCode"] = "" + pi.versionCode
                devInfo["MODEL"] = "" + Build.MODEL
                devInfo["SDK_INT"] = "" + Build.VERSION.SDK_INT
                devInfo["PRODUCT"] = "" + Build.PRODUCT
                devInfo["TIME"] = "" + currentTime
            }
        } catch (ignored: Exception) {
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                devInfo[field.name] = field.get(null).toString()
            } catch (ignored: Exception) {
            }

        }
    }

    /**
     * @param ex 抛出的异常
     */
    private fun saveExceptionToFile(ex: Throwable) {
        val sb = StringBuilder()
        for ((key, value) in devInfo) {
            sb.append(key).append("=").append(value).append("\n")
        }
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        try {
            val time = df.format(Date())
            val fileName = "$time.txt"
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val fos = FileOutputStream(
                    FileUtils.getDiskFileDir(
                        context,
                        "error"
                    ).path + "/" + fileName
                )
                fos.write(sb.toString().toByteArray())
                fos.close()
            }
        } catch (ignored: Exception) {

        }

    }
}