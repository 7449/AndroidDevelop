package framework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import framework.utils.RxUtils;

/**
 * by y on 2016/11/8
 */

public class App extends Application {
    private final List<Activity> activityList = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static App getInstance() {
        return (App) context;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        RxUtils.unsubscribe();
    }
}
