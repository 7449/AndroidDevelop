package accessibility.develop.android.install.app

import android.accessibilityservice.AccessibilityService
import android.annotation.TargetApi
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class InstallService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val eventType = event.eventType
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && event.packageName == getString(R.string.install_package_name)) {
            installation(event)
        }
    }

    override fun onInterrupt() {
    }


    private fun installation(event: AccessibilityEvent): Boolean {
        var nodeInfoList: List<AccessibilityNodeInfo>?
        val labels = arrayOf("确定", "安装", "下一步", "完成")
        for (label in labels) {
            nodeInfoList = event.source.findAccessibilityNodeInfosByText(label)
            if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
                if (click(nodeInfoList)) {
                    return true
                }
            }
        }
        return false
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun click(nodeInfoList: List<AccessibilityNodeInfo>): Boolean {
        for (node in nodeInfoList) {
            if (node.isClickable && node.isEnabled) {
                return node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }
        return false
    }
}
