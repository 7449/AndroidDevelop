package com.fuckapp.fragment.model;

import android.graphics.drawable.Drawable;

/**
 * by y on 2016/10/20.
 */

public class AppModel {


    private String appLabel;
    private Drawable appIcon;
    private String pkgName;

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appName) {
        this.appLabel = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
