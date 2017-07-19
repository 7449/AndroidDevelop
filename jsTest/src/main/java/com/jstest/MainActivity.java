package com.jstest;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;


/**
 * 简单的java与java互调
 * github.com/cordova
 * cordova 支持较好，并且相比WebView  兼容性及bug较少
 */
public class MainActivity extends AppCompatActivity {

    private static final String URL = "file:///android_asset/index.html";

    private WebView webView;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);
        initClick();
        webView.loadUrl(URL);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);


        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.addJavascriptInterface(new JsInterface(), "methodName");


    }

    private void initClick() {
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("javascript:toJsMessage(\"" + "java 传递消息到 js" + "\")");
            }
        });
        findViewById(R.id.btn_kitkat).setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                //返回值最好以JSON格式返回
                webView.evaluateJavascript("getKitkatMessage()", new ValueCallback<String>() {

                    @Override
                    public void onReceiveValue(String value) {
                        Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        findViewById(R.id.btn_kitkat_).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext()).setMessage(
                        "4.4之前没有提供直接调用js函数并获取值的方法，所以兼容的办法是 java调用js，js再次调用java将值返回"
                ).show();
            }
        });
    }

    private class JsInterface {

        @JavascriptInterface
        void toJavaMessage(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }


}
