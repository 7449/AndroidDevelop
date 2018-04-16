package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.ui.activity.BaseActivity;
import com.example.ui.activity.ExampleAlbumActivity;
import com.example.ui.activity.ExampleBannerActivity;
import com.example.ui.activity.ExampleNetActivity;
import com.example.ui.activity.ExampleStatusActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Override
    protected void initCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.banner_example, R.id.album_example, R.id.net_example, R.id.status_layout_example})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.banner_example:
                start(ExampleBannerActivity.class);
                break;
            case R.id.album_example:
                start(ExampleAlbumActivity.class);
                break;
            case R.id.net_example:
                start(ExampleNetActivity.class);
                break;
            case R.id.status_layout_example:
                start(ExampleStatusActivity.class);
                break;
        }
    }


    private void start(Class cls) {
        Intent intent = new Intent(getApplicationContext(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
