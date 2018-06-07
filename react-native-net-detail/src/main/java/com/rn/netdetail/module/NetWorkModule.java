package com.rn.netdetail.module;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.rn.netdetail.db.ObjectBoxNetEntity;
import com.rn.netdetail.db.ObjectBoxUtils;

import io.objectbox.Box;

/**
 * post parameter
 */
public class NetWorkModule extends ReactContextBaseJavaModule {

    private static final String DEFAULT = "success";
    private static final String ERROR = "error";
    private Box<ObjectBoxNetEntity> listBox;

    public NetWorkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        listBox = ObjectBoxUtils.getListBox();
    }

    @Override
    public String getName() {
        return "NetWorkDetail";
    }

    @ReactMethod
    public void writeMessage(String type, String url, String headers, String content, String method, String parameter) {
        ObjectBoxNetEntity entity = new ObjectBoxNetEntity();
        entity.url = url;
        entity.headers = headers;
        entity.content = content;
        entity.method = method;
        entity.parameter = parameter;
        listBox.put(entity);
    }

}
