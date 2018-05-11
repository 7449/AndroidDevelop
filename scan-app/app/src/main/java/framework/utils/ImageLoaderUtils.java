package framework.utils;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lock.R;

/**
 * by y on 2017/2/17
 */

public class ImageLoaderUtils {

    public static void display(ImageView imageView, Uri url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(imageView.getContext()).load(url).error(R.drawable.ic_header).centerCrop().into(imageView);
    }
}
