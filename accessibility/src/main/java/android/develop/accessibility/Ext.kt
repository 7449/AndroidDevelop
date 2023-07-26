package android.develop.accessibility

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.provider.Settings

val INSTALL_SERVER_PACKAGE_NAME = InstallService::class.java.canonicalName.orEmpty()

@Suppress("DEPRECATION")
fun Activity.serviceIsRunning(name: String): Boolean {
    val activityManager = getSystemService(ActivityManager::class.java)
    val services = activityManager.getRunningServices(Int.MAX_VALUE)
    return services.find { it.service.className == name } != null
}

fun Activity.startAccessibilityInstallAppService() {
    if (!serviceIsRunning(INSTALL_SERVER_PACKAGE_NAME)) {
        startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }
}

