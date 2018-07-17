package com.api;

import android.app.Activity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * by y.
 * <p>
 * Description:
 */
public class IView {
    private static final Map<String, ViewBind> binderMap = new LinkedHashMap<>();

    public static void bind(Activity activity) {
        String className = activity.getClass().getName();
        try {
            ViewBind binder = binderMap.get(className);
            if (binder == null) {
                binder = (ViewBind) Class.forName(className + "_Bind").newInstance();
                binderMap.put(className, binder);
            }
            if (binder != null) {
                binder.bindView(activity);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static void unBind(Object obj) {
        String className = obj.getClass().getName();
        ViewBind binder = binderMap.get(className);
        if (binder != null) {
            binder.unBind();
        }
        binderMap.remove(className);
    }
}
