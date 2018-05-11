package com.bilibilirecommend.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * by y on 2016/9/13
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.BackHandledInterface {

    public BaseFragment baseFragment;
    public Bundle mBundle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mBundle = getIntent().getExtras();
        initById();
        initCreate(savedInstanceState);
    }


    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.baseFragment = selectedFragment;
    }

    protected abstract void initCreate(Bundle savedInstanceState);

    protected abstract void initById();

    protected abstract int getLayoutId();

    @Override
    public void onBackPressed() {
        if (baseFragment == null || !baseFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

}
