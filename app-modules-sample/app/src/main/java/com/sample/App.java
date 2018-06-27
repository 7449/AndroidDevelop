package com.sample;

import android.app.Activity;
import android.support.v4.util.SimpleArrayMap;

import com.common.CommonApplication;
import com.common.router.IRouter;
import com.common.router.Router;
import com.common.router.RouterConst;

public class App extends CommonApplication implements IRouter {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.getInstance().init(this);
    }

    @Override
    public void registerActivity(SimpleArrayMap<String, Class<? extends Activity>> routerMap) {
        routerMap.put(RouterConst.ACT_MAIN_KEY, MainActivity.class);
    }

}
