package com.api;

import android.app.Activity;
import android.view.View;

/**
 * by y.
 * <p>
 * Description:
 */
public class IView {

    private static final String CLASS_SUFFIX = "_Bind";

    public static ViewBind bind(Activity obj) {
        return bind(obj, obj.getWindow().getDecorView());
    }

    public static ViewBind bind(Object obj, View view) {
        ViewBind bind = null;
        try {
            bind = (ViewBind) Class.forName(obj.getClass().getName() + CLASS_SUFFIX).newInstance();
            bind.bindView(obj, view);
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
