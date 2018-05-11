package framework;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import framework.utils.SPUtils;

/**
 * by y on 2017/2/14
 */

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static App getContext() {
        return (App) context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SPUtils.init(getApplicationContext());
    }
}
