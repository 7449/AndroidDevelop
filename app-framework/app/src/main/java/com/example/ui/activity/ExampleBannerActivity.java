package com.example.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.R;
import com.example.ui.fragment.ExampleBannerFragment;

/**
 * by y.
 * <p>
 * Description:
 */

public class ExampleBannerActivity extends BaseActivity {


    @Override
    protected void initCreate(@Nullable Bundle savedInstanceState) {
        replaceFragment(R.id.fragment, new ExampleBannerFragment());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }
}
