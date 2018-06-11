package com.rn.netdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {

    public static Bitmap captureContent(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    public static String saveImage(Context context, Bitmap bmp) {
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(getDiskFileDir(context, "image"), fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getPath();
    }

    public static void shareImage(Context context, String path) {
        Intent imageIntent = new Intent(Intent.ACTION_SEND);
        imageIntent.setType("image/jpeg");
        imageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
        context.startActivity(Intent.createChooser(imageIntent, "分享"));
    }

    public static File getDiskFileDir(Context context, String fileName) {
        String path;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            File externalFilesDir = context.getExternalFilesDir(fileName);
            path = externalFilesDir.getPath();
        } else {
            path = context.getCacheDir().getPath();
        }
        return new File(path);
    }
}
