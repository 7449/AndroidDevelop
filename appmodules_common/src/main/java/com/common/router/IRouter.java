package com.common.router;

import android.app.Activity;

import androidx.collection.SimpleArrayMap;

public interface IRouter {
    void registerActivity(SimpleArrayMap<String, Class<? extends Activity>> routerMap);
}
