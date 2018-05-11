package com.example.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.R;
import com.example.ui.fragment.ExampleStatusFragment;

/**
 * by y.
 * <p>
 * Description:
 */

public class ExampleStatusActivity extends BaseActivity {
    @Override
    protected void initCreate(@Nullable Bundle savedInstanceState) {
        replaceFragment(R.id.fragment, new ExampleStatusFragment());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }
}
