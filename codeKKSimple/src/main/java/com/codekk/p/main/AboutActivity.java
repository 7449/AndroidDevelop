package com.codekk.p.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;

import com.codekk.p.R;

import framework.base.BaseToolBarActivity;
import framework.data.Constant;
import framework.utils.ApkUtils;
import framework.utils.StatusBarUtil;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/30.
 */
public class AboutActivity extends BaseToolBarActivity implements View.OnClickListener {

    private CollapsingToolbarLayout mCollapsingToolbar;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageView(this, getView(R.id.image));
        mCollapsingToolbar.setCollapsedTitleTextColor(UIUtils.getColor(R.color.white));
    }

    @Override
    protected void initById() {
        mCollapsingToolbar = getView(R.id.collapsing_toolbar);
        getView(R.id.tv_wechat).setOnClickListener(this);
        getView(R.id.tv_qq).setOnClickListener(this);
        getView(R.id.tv_email).setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected boolean isToolbar() {
        return true;
    }

    @Override
    protected String toolbarTitle() {
        return getString(R.string.about);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wechat:
                if (ApkUtils.isApk(this, Constant.WE_CHAT_PACKAGE)) {
                    UIUtils.copy(Constant.WE_CHAT);
                    Intent intent = getPackageManager().getLaunchIntentForPackage(Constant.WE_CHAT_PACKAGE);
                    startActivity(intent);
                    UIUtils.Toast("微信号已复制到粘贴板");
                } else {
                    UIUtils.SnackBar(getView(R.id.coordinatorLayout), getString(R.string.package_null));
                }
                break;
            case R.id.tv_qq:
                if (ApkUtils.isApk(this, Constant.QQ_PACKAGE)) {
                    UIUtils.copy(Constant.QQ);
                    Intent intent = getPackageManager().getLaunchIntentForPackage(Constant.QQ_PACKAGE);
                    startActivity(intent);
                    UIUtils.Toast("qq号已复制到粘贴板");
                } else {
                    UIUtils.SnackBar(getView(R.id.coordinatorLayout), getString(R.string.package_null));
                }
                break;
            case R.id.tv_email:
                UIUtils.copy(Constant.E_MAIL);
                UIUtils.Toast("邮箱已复制到粘贴板");
                break;
        }
    }
}
