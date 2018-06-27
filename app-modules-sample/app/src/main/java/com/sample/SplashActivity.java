package com.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.common.router.Router;
import com.common.router.RouterConst;
import com.common.ui.CommonActivity;
import com.common.util.UIUtils;

public class SplashActivity extends CommonActivity {


    @Override
    protected void initById() {
    }

    @Override
    protected void initCreate(@Nullable Bundle savedInstanceState) {
        UIUtils.hideStatusBar(this);
        new Handler().postDelayed(() -> {
            Router
                    .getInstance()
                    .with(RouterConst.ACT_MAIN_KEY)
                    .navigation(this);
            this.finish();
        }, 1500);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
