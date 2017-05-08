package framework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import framework.utils.SPUtils;
import rx.Subscription;


/**
 * by y on 2016/7/26.
 */
public class App extends Application {
    public static Subscription subscription;
    private List<Activity> activityList = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SPUtils.init(this);
    }


    public static App getInstance() {
        return (App) context;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        unsubscribe();
    }

    public static void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
