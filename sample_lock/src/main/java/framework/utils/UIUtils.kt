package framework.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * by y on 2017/2/15
 */

object UIUtils {

    fun SnackBar(view: View, `object`: Any) {
        Snackbar.make(view, `object`.toString(), Snackbar.LENGTH_SHORT).show()
    }

    fun SnackBar(view: View, `object`: Any, color: Int) {
        Snackbar.make(view, `object`.toString(), Snackbar.LENGTH_SHORT)
            .setActionTextColor(color)
            .show()
    }

}
