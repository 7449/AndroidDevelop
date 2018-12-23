package sample.util.develop.android.js

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.widget.Toast
import kotlinx.android.synthetic.main.js_activity_main.*


/**
 * 简单的java与java互调
 * github.com/cordova
 * cordova 支持较好，并且相比WebView  兼容性及bug较少
 */
class JsMainActivity : AppCompatActivity() {


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
