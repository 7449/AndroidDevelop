package com.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

/**
 * SplashFragment实现
 * <p>
 * 1. 通过 replace remove Fragment去实现Splash页面ImageView的显示,并且进一步使用ViewStub优化进入首页的效率
 * <p>
 * 2. 进来Splash的时候可加载一些初始化数据，也可以请求网络数据。
 * <p>
 * 3.也可以让用户选择是否显示Splash. 例如BiliBili
 */
public class MainActivity extends AppCompatActivity {

    private ViewStub viewStub;
    private boolean isSplash = true; //增加标志 是否显示SplashFragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_layout);
        viewStub = findViewById(R.id.view_stub);

        if (isSplash) {
            final SplashFragment splashFragment = SplashFragment.getSplash();
            Toast.makeText(this, "加载 SplashFragment,此时可直接请求网络数据", Toast.LENGTH_SHORT).show();
            replaceFragment(splashFragment);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    removeFragment(splashFragment);
                    Toast.makeText(getApplicationContext(), "remove SplashFragment, viewStub Inflate 显示数据", Toast.LENGTH_SHORT).show();
                    showMain();
                }
            }, 3000);
        } else {
            showMain();
        }

    }

    private void showMain() {
        View inflate = viewStub.inflate();
    }

    private void replaceFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, fragment).commit();
    }

    private void removeFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
}
