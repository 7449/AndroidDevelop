package com.common.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * by y.
 * <p>
 * Description:
 */
public class DensityUtils {

    private static float density;
    private static float scaledDensity;

    public static void setDensity(Activity activity, Application application) {
        final DisplayMetrics applicationDisplayMetrics = application.getResources().getDisplayMetrics();
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        if (density == 0) {
            density = applicationDisplayMetrics.density;
            scaledDensity = applicationDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        scaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = applicationDisplayMetrics.widthPixels / 360;
        final float targetScaledDensity = targetDensity * (scaledDensity / density);
        final int targetDensityDpi = (int) (160 * targetDensity);

        applicationDisplayMetrics.density = targetDensity;
        applicationDisplayMetrics.scaledDensity = targetScaledDensity;
        applicationDisplayMetrics.densityDpi = targetDensityDpi;

        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
