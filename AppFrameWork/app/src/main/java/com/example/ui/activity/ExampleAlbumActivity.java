package com.example.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.R;
import com.example.ui.fragment.ExampleAlbumFragment;

/**
 * by y.
 * <p>
 * Description:
 */

public class ExampleAlbumActivity extends BaseActivity {


    @Override
    protected void initCreate(@Nullable Bundle savedInstanceState) {
        replaceFragment(R.id.fragment, new ExampleAlbumFragment());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fragment;
    }
}
