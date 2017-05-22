package framework.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * by y on 2017/2/15
 */

public class UIUtils {

    public static void SnackBar(View view, Object object) {
        Snackbar.make(view, object.toString(), Snackbar.LENGTH_SHORT).show();
    }

    public static void SnackBar(View view, Object object, int color) {
        Snackbar.make(view, object.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(color)
                .show();
    }

}
