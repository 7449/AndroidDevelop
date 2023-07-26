package sample.util.develop.android.statusbar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import sample.util.develop.android.statusbar.status.StatusBarUtils

class StatusBarMainActivity : AppCompatActivity() {

    private val btnStatusBarClass by lazy { findViewById<View>(R.id.btnStatusBarClass) }
    private val btnStatusBar by lazy { findViewById<View>(R.id.btnStatusBar) }
    private val btnStatusBarText by lazy { findViewById<View>(R.id.btnStatusBarText) }
    private val btnStatusBarTextFlyMe by lazy { findViewById<View>(R.id.btnStatusBarTextFlyMe) }
    private val btnStatusBarTextMiUi by lazy { findViewById<View>(R.id.btnStatusBarTextMiUi) }
    private val rootView by lazy { findViewById<View>(R.id.rootView) }

    private val color = intArrayOf(
        R.color.colorAccent,
        R.color.colorPrimary,
        R.color.colorPrimaryDark,
        R.color.colorWhite,
        R.color.colorBlack
    )
    private var isStatusBarTextColor = false
    private var isStatusBarTextFlymeColor = false
    private var isStatusBarTextMiuiColor = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.status_bar_activity_main)
        btnStatusBarClass.setOnClickListener {
            StatusBarUtils[this].reflectAlterStatusBarColor(
                ContextCompat.getColor(
                    applicationContext,
                    color[(color.size * Math.random()).toInt()]
                )
            )
        }

        btnStatusBar.setOnClickListener {
            StatusBarUtils[this].alterStatusBarColor(
                ContextCompat.getColor(
                    applicationContext,
                    color[(color.size * Math.random()).toInt()]
                )
            )
        }

        btnStatusBarText.setOnClickListener {
            isStatusBarTextColor = !isStatusBarTextColor
            rootView.fitsSystemWindows = isStatusBarTextColor
            StatusBarUtils[this].alterStatusBarTextColor(isStatusBarTextColor)
        }

        btnStatusBarTextFlyMe.setOnClickListener {
            isStatusBarTextFlymeColor = !isStatusBarTextFlymeColor
            StatusBarUtils[this].setStatusBarDarkIcon(isStatusBarTextFlymeColor)
        }

        btnStatusBarTextMiUi.setOnClickListener {
            isStatusBarTextMiuiColor = !isStatusBarTextMiuiColor
            StatusBarUtils[this].miuiSetStatusBarLightMode(isStatusBarTextMiuiColor)
        }
    }
}
