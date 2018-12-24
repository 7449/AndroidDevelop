package com.common.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

/**
 * @author y
 */
public class ViewUtils {

    public static void goneView(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    public static void visibleView(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void inVisibleView(View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static Bitmap toBitmap(View view, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

}
