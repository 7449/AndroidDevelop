package com.error.collect.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * by y.
 * <p>
 * Description:
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    @SuppressLint("StaticFieldLeak")
    private static ExceptionHandler instance = new ExceptionHandler();
    private Context context;
    private Thread.UncaughtExceptionHandler defaultHandler;
    private Map<String, String> devInfo = new HashMap<>();
    private DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());

    public static ExceptionHandler getInstance() {
        return instance;
    }

    public void setCustomCrashHandler(Context ctx) {
        context = ctx;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * @param thread 发生异常的线程
     * @param ex     抛出的异常
     * @return void
     * @name uncaughtException(Thread thread, Throwable ex)
     * @description 当发生UncaughtException时会回调此函数
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        boolean isDone = doException(ex);
        if (!isDone && defaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(thread, ex);
        } else {
            // 如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {

            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * @param ex 抛出的异常
     * @return 异常处理标志
     */
    private boolean doException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "程序出现错误退出！", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        collectDeviceInfo(context);
        saveExceptionToFile(ex);
        return true;
    }


    /**
     * @param ctx
     * @return void
     * @name collectDeviceInfo(Context ctx)
     * @description 收集必须的设备信息
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                devInfo.put("versionName", pi.versionName);
                devInfo.put("versionCode", "" + pi.versionCode);
                devInfo.put("MODEL", "" + Build.MODEL);
                devInfo.put("SDK_INT", "" + Build.VERSION.SDK_INT);
                devInfo.put("PRODUCT", "" + Build.PRODUCT);
                devInfo.put("TIME", "" + getCurrentTime());
            }
        } catch (Exception ignored) {
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                devInfo.put(field.getName(), field.get(null).toString());
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * @param ex 抛出的异常
     */
    private void saveExceptionToFile(Throwable ex) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : devInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            String time = df.format(new Date());
            String fileName = time + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                FileOutputStream fos = new FileOutputStream(FileUtils.getDiskFileDir(context, "error").getPath() + "/" + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * @return 当前时间
     */
    @SuppressLint("SimpleDateFormat")
    private static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}