package android.develop.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class InstallService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val eventType = event.eventType
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            click(arrayOf(
                "确定",
                "安装",
                "下一步",
                "完成",
                "更新"
            ).flatMap { event.source?.findAccessibilityNodeInfosByText(it).orEmpty() })
        }
    }

    override fun onInterrupt() {
    }

    private fun click(nodeInfoList: List<AccessibilityNodeInfo>) {
        nodeInfoList
            .filter { it.isClickable && it.isEnabled }
            .forEach { it.performAction(AccessibilityNodeInfo.ACTION_CLICK) }
    }

}
