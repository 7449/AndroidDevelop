package com.sample;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.common.router.Router;
import com.common.router.RouterConst;
import com.common.util.UIUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        UIUtils.hideStatusBar(this);
        new Handler().postDelayed(() -> {
            Router
                    .getInstance()
                    .with(RouterConst.ACT_MAIN_KEY)
                    .navigation(this);
            this.finish();
        }, 1500);
    }
}
