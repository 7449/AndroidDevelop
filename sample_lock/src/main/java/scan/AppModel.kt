package scan

import android.graphics.drawable.Drawable

/**
 * by y on 2016/10/20.
 */

class AppModel {

    var appLabel: String = ""
    lateinit var appIcon: Drawable
    var pkgName: String = ""

    constructor(appLabel: String, appIcon: Drawable, pkgName: String) {
        this.appLabel = appLabel
        this.appIcon = appIcon
        this.pkgName = pkgName
    }

    constructor()
}
