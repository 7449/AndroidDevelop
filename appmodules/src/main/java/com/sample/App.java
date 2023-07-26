package com.sample;

import android.app.Activity;

import androidx.collection.SimpleArrayMap;

import com.common.CommonApplication;
import com.common.router.IRouter;
import com.common.router.Router;
import com.common.router.RouterConst;

import io.reactivex.network.RxNetWork;

public class App extends CommonApplication implements IRouter {

    @Override
    public void onCreate() {
        super.onCreate();
        RxNetWork.getInstance().setBaseUrl("https://www.baidu.com");
        Router.getInstance().init(this);
    }

    @Override
    public void registerActivity(SimpleArrayMap<String, Class<? extends Activity>> routerMap) {
        routerMap.put(RouterConst.ACT_MAIN_KEY, MainActivity.class);
    }

}
