package com.example.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.R;
import com.example.ui.fragment.ExampleNetFragment;

/**
 * by y.
 * <p>
 * Description:
 */

public class ExampleNetActivity extends BaseActivity {


    @Override
    protected void initCreate(@Nullable Bundle savedInstanceState) {
        replaceFragment(R.id.fragment, new ExampleNetFragment());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }
}
