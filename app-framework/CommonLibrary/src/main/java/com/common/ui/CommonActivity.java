package com.common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.common.R;
import com.common.widget.status.StatusClickListener;
import com.common.widget.status.StatusLayout;

//
//                       .::::.
//                     .::::::::.
//                    :::::::::::
//                 ..:::::::::::'
//              '::::::::::::'
//                .::::::::::
//           '::::::::::::::..
//                ..::::::::::::.
//              ``::::::::::::::::
//               ::::``:::::::::'        .:::.
//              ::::'   ':::::'       .::::::::.
//            .::::'      ::::     .:::::::'::::.
//           .:::'       :::::  .:::::::::' ':::::.
//          .::'        :::::.:::::::::'      ':::::.
//         .::'         ::::::::::::::'         ``::::.
//     ...:::           ::::::::::::'              ``::.
//    ```` ':.          ':::::::::'                  ::::..
//                       '.:::::'                    ':'````..

/**
 * by y.
 * <p>
 * Description:Activity
 */

public abstract class CommonActivity extends AppCompatActivity implements StatusClickListener {

    protected StatusLayout mStatusLayout;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        mToolbar = findViewById(R.id.toolbar);
        mStatusLayout = findViewById(R.id.activity_status_layout);
        mStatusLayout.setSuccessView(getLayoutId());
        init();
        initCreate(savedInstanceState);
    }

    protected abstract void init();

    protected abstract void initCreate(@Nullable Bundle savedInstanceState);

    protected abstract int getLayoutId();


    @Override
    public void onEmptyClick() {

    }

    @Override
    public void onErrorClick() {

    }

    @Override
    public void onLoadingClick() {

    }

    @Override
    public void onNorMalClick() {

    }

    @Override
    public void onSuccessClick() {

    }
}
