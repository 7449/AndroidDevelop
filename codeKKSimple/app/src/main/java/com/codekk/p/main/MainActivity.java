package com.codekk.p.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codekk.p.R;
import com.codekk.p.projects.widget.ProjectsFragment;
import com.codekk.p.search.widget.SearchFragment;

import framework.base.BaseActivity;
import framework.data.Constant;
import framework.utils.RxBus;
import framework.utils.StatusBarUtil;
import framework.utils.UIUtils;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {

    private Toolbar mToolBar;
    private DrawerLayout mDrawerlayout;
    private FloatingActionButton mFAB;

    @SuppressLint("SetTextI18n")
    @Override
    protected void initCreate(Bundle savedInstanceState) {
        StatusBarUtil.setColorForDrawerLayout(this, mDrawerlayout, 0);
        mToolBar.setTitle(getString(R.string.project));
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.vector_drawable_menu);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public void onClick(View view) {
                mDrawerlayout.openDrawer(Gravity.LEFT);
            }
        });
        replaceFragment(ProjectsFragment.newInstance());
    }

    @Override
    protected void initById() {
        mToolBar = getView(R.id.toolbar);
        mDrawerlayout = getView(R.id.dl_layout);
        mFAB = getView(R.id.fa_btn);
        NavigationView mNavigationView = getView(R.id.navigationview);
        mNavigationView.setNavigationItemSelectedListener(this);
        mToolBar.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mToolBar.setTitle(item.getTitle());
        switch (item.getItemId()) {
            case R.id.project:
                hideFAB();
                replaceFragment(ProjectsFragment.newInstance());
                break;
            default:
                showFAB();
                replaceFragment(new SearchFragment());
                break;
        }
        mDrawerlayout.closeDrawers();
        return true;
    }

    private void showFAB() {
        mFAB.setVisibility(View.VISIBLE);
    }

    private void hideFAB() {
        mFAB.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                UIUtils.startActivity(AboutActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                RxBus.getInstance().send(Constant.ONCLICK);
                break;
        }
    }

}
