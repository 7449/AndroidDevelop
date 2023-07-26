package android.develop.accessibility

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val button by lazy { findViewById<Button>(R.id.installApp) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button.setOnClickListener { startAccessibilityInstallAppService() }
    }

    override fun onResume() {
        super.onResume()
        button.text = if (serviceIsRunning(INSTALL_SERVER_PACKAGE_NAME))
            "Install(Running)" else "Install(Not Running)"
    }

}
