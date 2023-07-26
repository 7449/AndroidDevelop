package sample.util.develop.android.js

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

/**
 * 简单的java与java互调
 * github.com/cordova
 * cordova 支持较好，并且相比WebView  兼容性及bug较少
 */
class JsMainActivity : AppCompatActivity() {

    private val webView by lazy { findViewById<WebView>(R.id.webView) }
    private val btn by lazy { findViewById<View>(R.id.btn) }
    private val btnKitkat by lazy { findViewById<View>(R.id.btnKitkat) }
    private val btnKitkat_ by lazy { findViewById<View>(R.id.btnKitkat_) }

    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.js_activity_main)
        initClick()
        webView.loadUrl(URL)

        val settings = webView.settings
        settings.javaScriptEnabled = true
        webView.webChromeClient = object : WebChromeClient() {
        }
        webView.addJavascriptInterface(JsInterface(), "methodName")
    }

    private fun initClick() {
        btn.setOnClickListener { webView.loadUrl("javascript:toJsMessage(\"" + "java 传递消息到 js" + "\")") }
        btnKitkat.setOnClickListener {
            //返回值最好以JSON格式返回
            webView.evaluateJavascript("getKitkatMessage()") { value ->
                Toast.makeText(
                    applicationContext,
                    value,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnKitkat_.setOnClickListener { view ->
            AlertDialog.Builder(view.context).setMessage(
                "4.4之前没有提供直接调用js函数并获取值的方法，所以兼容的办法是 java调用js，js再次调用java将值返回"
            ).show()
        }
    }

    private inner class JsInterface {

        @JavascriptInterface
        fun toJavaMessage(message: String) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }

    companion object {
        private const val URL = "file:///android_asset/index.html"
    }
}
