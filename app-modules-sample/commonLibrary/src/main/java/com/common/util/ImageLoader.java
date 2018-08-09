package com.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.common.R;

public class ImageLoader {

    private static RequestOptions requestOptions = new RequestOptions().error(R.drawable.ic_launcher);


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
