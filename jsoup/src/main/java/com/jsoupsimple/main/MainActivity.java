package com.jsoupsimple.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jsoupsimple.R;
import com.jsoupsimple.tab.TabFragment;

import framework.App;
import framework.base.BaseActivity;
import framework.data.Constant;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        toolbar.setTitle(getString(R.string.dbmz_title));
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(Constant.DOU_BAN_MEI_ZI);
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        drawerLayout = getView(R.id.dl_layout);
        navigationView = getView(R.id.navigationview);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            App.getInstance().exit();
            super.onBackPressed();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void replaceFragment(String type) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, TabFragment.newInstance(type)).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        toolbar.setTitle(item.getTitle());
        switch (item.getItemId()) {
            case R.id.dbmz:
                replaceFragment(Constant.DOU_BAN_MEI_ZI);
                break;
            case R.id.mzitu:
                replaceFragment(Constant.M_ZI_TU);
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }
}
