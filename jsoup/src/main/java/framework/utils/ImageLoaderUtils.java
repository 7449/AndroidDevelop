package framework.utils;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jsoupsimple.R;

/**
 * by y on 2016/7/26.
 */
public class ImageLoaderUtils {

    public static void display(ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(imageView.getContext()).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).crossFade().into(imageView);
    }

}
