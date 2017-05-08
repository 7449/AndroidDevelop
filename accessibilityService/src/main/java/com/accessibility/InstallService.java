package com.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class InstallService extends AccessibilityService {

    private static final String TAG = "InstallService";

    public InstallService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getSource() == null) {
            log("return  source is null");
            return;
        }
        int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.getPackageName().equals(getString(R.string.install_package_name))) {
            log(String.valueOf(installation(event)));
        }

    }

    @Override
    public void onInterrupt() {
        log("onInterrupt");
    }


    private boolean installation(AccessibilityEvent event) {
        List<AccessibilityNodeInfo> nodeInfoList;
        String[] labels = new String[]{"确定", "安装", "下一步", "完成"};
        for (String label : labels) {
            nodeInfoList = event.getSource().findAccessibilityNodeInfosByText(label);
            if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
                if (click(nodeInfoList)) {
                    return true;
                }
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private boolean click(List<AccessibilityNodeInfo> nodeInfoList) {
        for (AccessibilityNodeInfo node : nodeInfoList) {
            if (node.isClickable() && node.isEnabled()) {
                return node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
        return false;
    }

    private void log(String s) {
        Log.i(TAG, s);
    }
}
