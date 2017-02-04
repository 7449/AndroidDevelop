package com.fuckapp.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fuckapp.R;
import com.fuckapp.fragment.widget.AppFragment;
import com.fuckapp.utils.Constant;
import com.fuckapp.utils.SPUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!SPUtils.getBoolean(SPUtils.WARN_APP)) {
            new AlertDialog
                    .Builder(this)
                    .setMessage("手机需要用root权限")
                    .setPositiveButton("ok", null).create().show();
            SPUtils.setBoolean(SPUtils.WARN_APP, true);
        }
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_layout);
        toolbar.setTitle(getString(R.string.all_app));
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragment(Constant.ALL_APP);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        toolbar.setTitle(item.getTitle());
        switch (item.getItemId()) {
            case R.id.all_app:
                replaceFragment(Constant.ALL_APP);
                break;
            case R.id.system_app:
                replaceFragment(Constant.SYSTEM_APP);
                break;
            case R.id.no_system_app:
                replaceFragment(Constant.NO_SYSTEM_APP);
                break;
            case R.id.hide_app:
                replaceFragment(Constant.HIDE_APP);
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    private void replaceFragment(int type) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, AppFragment.start(type)).commit();
    }
}
