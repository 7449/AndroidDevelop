package org.apache.cordova.network;

import android.text.TextUtils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * by y on 28/07/2017.
 */

public class NetWorkPlugin extends CordovaPlugin {

    private static final String NET_WORK_ACTION = "requestNetWork";
    private static final int ERROR_ARGS = -1;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (TextUtils.equals(action, NET_WORK_ACTION)) {
            String type = args.getString(0);
            if (TextUtils.equals(type, String.valueOf(ERROR_ARGS))) {
                callbackContext.error("网络请求参数未配置");
                return false;
            }
        }
        callbackContext.error("action != requestNetWork");
        return false;
    }
}
