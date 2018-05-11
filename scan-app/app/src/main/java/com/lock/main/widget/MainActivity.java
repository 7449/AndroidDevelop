package com.lock.main.widget;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.lock.R;
import com.lock.checkapp.widget.CheckFragment;
import com.lock.main.Constant;
import com.lock.main.model.MainBean;
import com.lock.main.presenter.MainPresenter;
import com.lock.main.presenter.MainPresenterImpl;
import com.lock.main.view.MainView;
import com.lock.setting.widget.SettingFragment;
import com.lock.setting.widget.SettingFragment.ExitInterface;
import com.lock.showapp.widget.ShowAppFragment;
import com.tbruyelle.rxpermissions.RxPermissions;

import de.hdodenhof.circleimageview.CircleImageView;
import framework.base.BaseActivity;
import framework.sql.greendao.GreenDaoDbUtils;
import framework.utils.AppUtils;
import framework.utils.ImageLoaderUtils;
import framework.utils.SPUtils;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        LoginFragment.LoginClick,
        MainView, VerifyFragment.VerifyClick, ExitInterface {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private AppCompatTextView mTvUserName;
    private MainPresenter mPresenter;
    private CircleImageView mHeaderImageView;
    private RxPermissions mRxPermissions;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        mToolbar.setTitle(getString(R.string.main));
        mPresenter = new MainPresenterImpl(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mRxPermissions = RxPermissions.getInstance(getApplicationContext());
        if (!SPUtils.isLogin()) {
            replaceFragment(ShowAppFragment.newInstance());
            setUser(SPUtils.readeUser());
        } else {
            LoginFragment.newInstance().show(getSupportFragmentManager(), Constant.FRAGMENT_TAG);
        }
        mHeaderImageView.setOnClickListener(v -> mRxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        if (!AppUtils.openPick(this)) {
                            Toast.makeText(this, getString(R.string.open_pick_error), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.permission_error), Toast.LENGTH_SHORT).show();
                    }
                }));
        ImageLoaderUtils.display(mHeaderImageView, Uri.parse(SPUtils.getString(SPUtils.HEADER_URL, "")));
    }


    @Override
    protected void initById() {
        mToolbar = getView(R.id.toolbar);
        mDrawerLayout = getView(R.id.dl_layout);
        mNavigationView = getView(R.id.navigationview);
        mTvUserName = (AppCompatTextView) mNavigationView.getHeaderView(0).findViewById(R.id.user_config);
        mHeaderImageView = (CircleImageView) mNavigationView.getHeaderView(0).findViewById(R.id.header_iv);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        if (mNavigationView.getMenu().findItem(item.getItemId()).isChecked()) {
            mDrawerLayout.closeDrawers();
            return false;
        }
        switch (item.getItemId()) {
            case R.id.main:
                mToolbar.setTitle(item.getTitle());
                replaceFragment(ShowAppFragment.newInstance());
                break;
            default:
                VerifyFragment.newInstance(item.getItemId()).show(getSupportFragmentManager(), Constant.FRAGMENT_TAG);
                return false;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CANCELED && resultCode == Activity.RESULT_OK && data != null) {
            SPUtils.setString(SPUtils.HEADER_URL, data.getData().toString());
            ImageLoaderUtils.display(mHeaderImageView, data.getData());
        }
    }

    @Override
    public void onRegister(String userName, String passWord) {
        mPresenter.registerUser(userName, passWord);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setUser(MainBean user) {
        mTvUserName.setText(getString(R.string.main_header_user_name) + user.getUserName());
    }

    @Override
    public void registerSuccess() {
        replaceFragment(ShowAppFragment.newInstance());
    }

    @Override
    public void onVerifySuccess(int type) {
        switch (type) {
            case R.id.setting:
                mToolbar.setTitle(getString(R.string.setting));
                replaceFragment(SettingFragment.newInstance());
                mNavigationView.getMenu().findItem(R.id.setting).setChecked(true);
                break;
            case R.id.check_app:
                mToolbar.setTitle(getString(R.string.check_app));
                replaceFragment(CheckFragment.newInstance());
                mNavigationView.getMenu().findItem(R.id.check_app).setChecked(true);
                break;
        }
        mDrawerLayout.closeDrawers();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCancel() {
        finish();
    }

    @Override
    public void registerError() {
        Toast.makeText(getApplicationContext(), getString(R.string.main_register_fail), Toast.LENGTH_SHORT).show();
        onCancel();
    }

    @Override
    public void onVerifyError() {
        Toast.makeText(getApplicationContext(), getString(R.string.main_verify_fail), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void exit() {
        GreenDaoDbUtils.clearNormal();
        SPUtils.clearAll();
        onCancel();
    }
}
