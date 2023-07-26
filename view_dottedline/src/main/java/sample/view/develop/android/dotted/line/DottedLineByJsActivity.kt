package sample.view.develop.android.dotted.line

import android.graphics.Color
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class DottedLineByJsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dotted_line_activity_by_js)
        val webview = findViewById<WebView>(R.id.webview)
        val webSettings = webview.settings
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webview.setBackgroundColor(Color.TRANSPARENT)
        webview.loadUrl("file:///android_asset/html/DLAnim.html")
    }
}
