package com.readlist.main.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.readlist.R;
import com.readlist.main.presenter.MainPresenter;
import com.readlist.main.presenter.MainPresenterImpl;
import com.readlist.main.view.MainView;
import com.readlist.news.widget.NewsTabFragment;
import com.readlist.picture.widget.PictureFragment;
import com.readlist.weixin.widget.WXHotFragment;

import framework.App;
import framework.base.BaseActivity;
import framework.data.Constant;
import framework.utils.RxBus;
import framework.utils.StatusBarUtil;
import framework.utils.UIUtils;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView, OnClickListener {

    private Toolbar mToolBar;
    private DrawerLayout mDrawerlayout;
    private NavigationView mNavigationView;
    private MainPresenter mPresenter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        mPresenter = new MainPresenterImpl(this);
        StatusBarUtil.setColorForDrawerLayout(this, mDrawerlayout, 0);
        mNavigationView.setNavigationItemSelectedListener(this);
        mToolBar.setTitle(getString(R.string.weixin));
        mToolBar.setOnClickListener(this);
        switchWeixin();
    }

    @Override
    protected void initById() {
        mNavigationView = getView(R.id.navigationview);
        mToolBar = getView(R.id.toolbar);
        mDrawerlayout = getView(R.id.dl_layout);
        floatingActionButton = getView(R.id.fa_btn);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mToolBar.setTitle(item.getTitle());
        mPresenter.switchId(item.getItemId());
        mDrawerlayout.closeDrawers();
        return true;
    }

    @Override
    public void switchWeixin() {
        floatingActionButton.setVisibility(View.VISIBLE);
        replaceFragment(WXHotFragment.startWxHot());
    }

    @Override
    public void switchPicture() {
        floatingActionButton.setVisibility(View.VISIBLE);
        replaceFragment(PictureFragment.startWxHot());
    }

    @Override
    public void switchNews() {
        floatingActionButton.setVisibility(View.GONE);
        replaceFragment(NewsTabFragment.newInstance());
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                UIUtils.SnackBar(findViewById(R.id.coordinatorLayout), getString(R.string.exit_app));
                exitTime = System.currentTimeMillis();
            } else {
                App.getInstance().exit();
                RxBus.getInstance().clearAllRxBus();
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar:
                RxBus.getInstance().send(Constant.ONCLICK);
                break;
        }
    }
}
