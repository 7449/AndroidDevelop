package com.api;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * by y.
 * <p>
 * Description:
 */
public class IView {
    public static ViewBind bind(Activity obj) {
        ViewBind bind = null;
        String className = obj.getClass().getName();
        try {
            bind = (ViewBind) Class.forName(className + "_Bind").newInstance();
            if (bind != null) {
                bind.bindView(obj, obj.getWindow().getDecorView());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bind;
    }

    public static ViewBind bind(Fragment obj, View view) {
        ViewBind bind = null;
        String className = obj.getClass().getName();
        try {
            bind = (ViewBind) Class.forName(className + "_Bind").newInstance();
            if (bind != null) {
                bind.bindView(obj, view);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bind;
    }
}
