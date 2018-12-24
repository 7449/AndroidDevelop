package framework.utils

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.TextView

/**
 * by y on 2017/2/14
 */

object TextDrawableUtils {

    fun setAppDrawable(textView: TextView, drawable: Drawable) {
        drawable.setBounds(0, 0, 100, 100)
        textView.setCompoundDrawables(null, drawable, null, null)
        textView.compoundDrawablePadding = 10
        textView.gravity = Gravity.CENTER
    }
}
