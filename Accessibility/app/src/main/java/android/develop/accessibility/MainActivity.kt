package android.develop.accessibility

import android.app.Activity
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

fun Activity.serviceIsRunning(name: String): Boolean {
    val am = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val services = am.getRunningServices(Int.MAX_VALUE)
    for (info in services) {
        if (info.service.className == name) {
            return true
        }
    }
    return false
}

fun Activity.startAccessibilityInstallAppService() {
    if (serviceIsRunning(INSTALL_SERVER_PACKAGE_NAME)) {
        Toast.makeText(this, "自动安装App服务已经在运行", Toast.LENGTH_LONG).show()
    } else {
        AlertDialog.Builder(this)
            .setTitle("Server")
            .setPositiveButton("开启自动安装App") { _, _ -> startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
            .show()
    }
}

const val INSTALL_SERVER_PACKAGE_NAME = "accessibility.develop.android.install.app.InstallService"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        installApp.setOnClickListener { startAccessibilityInstallAppService() }
    }
}
