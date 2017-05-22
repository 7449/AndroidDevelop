package framework.utils;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.TextView;

/**
 * by y on 2017/2/14
 */

public class TextDrawableUtils {

    public static void setAppDrawable(TextView textView, Drawable drawable) {
        drawable.setBounds(0, 0, 100, 100);
        textView.setCompoundDrawables(null, drawable, null, null);
        textView.setCompoundDrawablePadding(10);
        textView.setGravity(Gravity.CENTER);
    }
}
