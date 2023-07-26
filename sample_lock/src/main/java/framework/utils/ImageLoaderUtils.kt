package framework.utils

import android.net.Uri
import android.widget.ImageView

import com.bumptech.glide.Glide

/**
 * by y on 2017/2/17
 */

object ImageLoaderUtils {

    fun display(imageView: ImageView?, url: Uri) {
        if (imageView == null) {
            throw IllegalArgumentException("argument error")
        }
        Glide.with(imageView.context).load(url).into(imageView)
    }
}
