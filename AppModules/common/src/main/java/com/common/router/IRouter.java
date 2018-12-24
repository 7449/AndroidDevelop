package com.common.router;

import android.app.Activity;
import android.support.v4.util.SimpleArrayMap;

public interface IRouter {
    void registerActivity(SimpleArrayMap<String, Class<? extends Activity>> routerMap);
}
