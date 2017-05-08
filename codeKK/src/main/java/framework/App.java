package framework;

import android.app.Application;
import android.content.Context;

/**
 * by y on 2016/8/7.
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static App getInstance() {
        return (App) context;
    }
}
