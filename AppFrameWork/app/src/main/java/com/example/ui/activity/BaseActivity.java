package com.example.ui.activity;

import android.support.v4.app.Fragment;

import com.common.ui.CommonActivity;
import com.example.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * by y.
 * <p>
 * Description:
 */

public abstract class BaseActivity extends CommonActivity {

    private Unbinder bind;

    @Override
    protected void init() {
//        mStatusLayout.setErrorView(View);
//        mStatusLayout.setEmptyView(View);
//        mStatusLayout.setLoadingView(View);
//        mStatusLayout.setNorMalView(View);
//        mStatusLayout.setStatusClickListener(
//                new StatusClickListener() {
//                    @Override
//                    public void onNorMalClick() {
//
//                    }
//
//                    @Override
//                    public void onLoadingClick() {
//
//                    }
//
//                    @Override
//                    public void onEmptyClick() {
//
//                    }
//
//                    @Override
//                    public void onSuccessClick() {
//
//                    }
//
//                    @Override
//                    public void onErrorClick() {
//
//                    }
//                });
        bind = ButterKnife.bind(this);
        mToolbar.setTitle(R.string.app_name);
    }

    public void replaceFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
