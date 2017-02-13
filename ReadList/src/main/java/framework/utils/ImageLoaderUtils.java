package framework.utils;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.readlist.R;

/**
 * by y on 2016/8/7.
 */
public class ImageLoaderUtils {

    public static void display(ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(imageView.getContext()).load(url).placeholder(placeholder)
                .error(error).centerCrop().into(imageView);
    }

    public static void display(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).centerCrop().into(imageView);
    }
}
