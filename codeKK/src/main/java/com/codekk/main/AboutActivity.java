package com.codekk.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.codekk.R;

import butterknife.BindView;
import butterknife.OnClick;
import framework.base.BaseActivity;
import framework.data.Constant;
import framework.utils.StatusBarUtil;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/30.
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageView(this, image);
        collapsingToolbar.setCollapsedTitleTextColor(UIUtils.getColor(R.color.white));
        mToolbar.setTitle(getString(R.string.about));
        mToolbar.setNavigationIcon(R.drawable.vector_drawable_finish);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @OnClick({R.id.tv_wechat, R.id.tv_qq, R.id.tv_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wechat:
                Intent weChatIntent = getPackageManager().getLaunchIntentForPackage(Constant.WE_CHAT_PACKAGE);
                if (weChatIntent != null) {
                    startActivity(weChatIntent);
                    UIUtils.copy(Constant.WE_CHAT);
                    UIUtils.toast("微信号已复制到粘贴板");
                } else {
                    UIUtils.snackBar(coordinatorLayout, getString(R.string.package_null));
                }
                break;
            case R.id.tv_qq:
                Intent qqIntent = getPackageManager().getLaunchIntentForPackage(Constant.QQ_PACKAGE);
                if (qqIntent != null) {
                    UIUtils.copy(Constant.QQ);
                    startActivity(qqIntent);
                    UIUtils.toast("qq号已复制到粘贴板");
                } else {
                    UIUtils.snackBar(coordinatorLayout, getString(R.string.package_null));
                }
                break;
            case R.id.tv_email:
                UIUtils.copy(Constant.E_MAIL);
                UIUtils.toast("邮箱已复制到粘贴板");
                break;
        }
    }

}
