package com.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class ImageLoader {

    private static RequestOptions requestOptions = new RequestOptions();


    public static void display(@NonNull ImageView imageView, int url) {
        Glide.with(imageView.getContext()).load(url).apply(requestOptions).into(imageView);
    }

    public static void display(@NonNull ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).apply(requestOptions).into(imageView);
    }

    public static void display(@NonNull ImageView imageView, Object url) {
        Glide.with(imageView.getContext()).load(url).apply(requestOptions).into(imageView);
    }

    public static void displayBitmap(@NonNull Context context, String path, Target<Bitmap> target) {
        Glide.with(context).asBitmap().load(path).into(target);
    }

    public static void displayDrawable(@NonNull Context context, String path, Target<Drawable> target) {
        Glide.with(context).asDrawable().load(path).into(target);
    }

}
