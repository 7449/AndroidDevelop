package com.codekk.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codekk.R;
import com.codekk.projects.widget.ProjectsFragment;
import com.codekk.search.widget.SearchFragment;
import com.rxnetwork.manager.RxDisposeManager;

import butterknife.BindView;
import butterknife.OnClick;
import framework.base.BaseActivity;
import framework.base.BaseFragment;
import framework.data.OnToolBarClickerListener;
import framework.utils.StatusBarUtil;
import framework.utils.UIUtils;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.fa_btn)
    FloatingActionButton fbt;
    @BindView(R.id.navigationview)
    NavigationView navigationview;
    @BindView(R.id.dl_layout)
    DrawerLayout mDrawerLayout;

    private Fragment fragment;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        StatusBarUtil.setColorForDrawerLayout(this, mDrawerLayout, 0);
        mToolBar.setTitle(getString(R.string.project));
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.drawable.vector_drawable_menu);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        fragment = ProjectsFragment.newInstance();
        replaceFragment(fragment);
        navigationview.setNavigationItemSelectedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mToolBar.setTitle(item.getTitle());
        switch (item.getItemId()) {
            case R.id.project:
                fbt.setVisibility(View.GONE);
                fragment = ProjectsFragment.newInstance();
                break;
            default:
                fbt.setVisibility(View.VISIBLE);
                fragment = SearchFragment.newInstance();
                break;
        }
        replaceFragment(fragment);
        mDrawerLayout.closeDrawers();
        return true;
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


    @OnClick(R.id.toolbar)
    public void onViewClicked() {
        if (fragment != null && fragment instanceof BaseFragment) {
            OnToolBarClickerListener listener = (OnToolBarClickerListener) fragment;
            listener.onToolbarClick();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RxDisposeManager.getInstance().clearDispose();
    }
}
