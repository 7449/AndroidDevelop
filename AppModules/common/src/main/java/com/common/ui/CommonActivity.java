package com.common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.common.R;
import com.common.util.DensityUtils;
import com.status.layout.OnStatusClickListener;
import com.status.layout.StatusLayout;

import org.jetbrains.annotations.NotNull;

/**
 * by y.
 * <p>
 * Description:Activity
 */

public abstract class CommonActivity extends AppCompatActivity implements OnStatusClickListener {

    protected StatusLayout mStatusLayout;
    protected Toolbar mToolbar;
    @Nullable
    protected Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DensityUtils.setDensity(this, getApplication());
        setContentView(R.layout.activity_common);
        bundle = getIntent().getExtras();
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setVisibility(showToolBar() ? View.VISIBLE : View.GONE);
        if (showToolBar()) {
            mToolbar.setTitle(title());
        }
        mStatusLayout = findViewById(R.id.activity_status_layout);
        mStatusLayout.addSuccessView(getLayoutId());
        mStatusLayout.setOnStatusClickListener(this);
        initById();
        initCreate(savedInstanceState);
    }

    protected abstract void initById();

    protected abstract void initCreate(@Nullable Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected boolean showToolBar() {
        return false;
    }

    protected CharSequence title() {
        return "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onEmptyClick(@NotNull View view) {

    }

    @Override
    public void onErrorClick(@NotNull View view) {

    }

    @Override
    public void onLoadingClick(@NotNull View view) {

    }

    @Override
    public void onNorMalClick(@NotNull View view) {

    }

    @Override
    public void onSuccessClick(@NotNull View view) {

    }
}
