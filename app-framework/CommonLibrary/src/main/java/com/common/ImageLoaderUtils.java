package com.common;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * by y.
 * <p>
 * Description:
 */

public class ImageLoaderUtils {

    public static void display(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide
                .with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

}
