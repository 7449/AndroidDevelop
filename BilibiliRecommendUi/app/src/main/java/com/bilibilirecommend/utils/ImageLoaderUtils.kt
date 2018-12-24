package com.bilibilirecommend.utils


import android.widget.ImageView

import com.bilibilirecommend.App
import com.bilibilirecommend.R
import com.bumptech.glide.Glide

object ImageLoaderUtils {
    fun display(imageView: ImageView?, url: String) {
        if (imageView == null) {
            throw IllegalArgumentException("argument error")
        }
        Glide.with(App.instance).load(url).placeholder(R.drawable.bili_default_image_tv)
                .error(R.drawable.bili_default_image_tv).centerCrop().into(imageView)
    }
}
