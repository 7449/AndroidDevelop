package com.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.readlibrary.ReadMainFragment;
import com.reportlibrary.ReportMainFragment;
import com.userlibrary.UserMainFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment fragmentRead;
    private Fragment fragmentUser;
    private Fragment fragmentPort;

    private static final String FRAGMENT_READ = "FRAGMENT_READ";
    private static final String FRAGMENT_PORT = "FRAGMENT_PORT";
    private static final String FRAGMENT_USER = "FRAGMENT_USER";
    private RadioGroup mRadioGroup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRadioGroup = findViewById(R.id.rg_group);
        setTabSelect(0);
        mRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_read:
                    setTabSelect(0);
                    break;
                case R.id.rb_report:
                    setTabSelect(1);
                    break;
                case R.id.rb_user:
                    setTabSelect(2);
                    break;
            }
        });
    }

    private void setTabSelect(int i) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (i) {
            case 0:
                fragmentRead = manager.findFragmentByTag(FRAGMENT_READ);
                hideTab(transaction);
                if (null == fragmentRead) {
                    fragmentRead = ReadMainFragment.newInstance();
                    transaction.add(R.id.fragment, fragmentRead, FRAGMENT_READ);
                } else {
                    transaction.show(fragmentRead);
                }
                break;
            case 1:
                fragmentPort = manager.findFragmentByTag(FRAGMENT_PORT);
                hideTab(transaction);
                if (null == fragmentPort) {
                    fragmentPort = ReportMainFragment.newInstance();
                    transaction.add(R.id.fragment, fragmentPort, FRAGMENT_PORT);
                } else {
                    transaction.show(fragmentPort);
                }
                break;
            case 2:
                fragmentUser = manager.findFragmentByTag(FRAGMENT_USER);
                hideTab(transaction);
                if (null == fragmentUser) {
                    fragmentUser = UserMainFragment.newInstance();
                    transaction.add(R.id.fragment, fragmentUser, FRAGMENT_USER);
                } else {
                    transaction.show(fragmentUser);
                }
                break;
        }
        transaction.commit();
    }


    private void hideTab(FragmentTransaction transaction) {
        if (null != fragmentRead) {
            transaction.hide(fragmentRead);
        }
        if (null != fragmentPort) {
            transaction.hide(fragmentPort);
        }
        if (null != fragmentUser) {
            transaction.hide(fragmentUser);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearTab();
    }

    private void clearTab() {
        if (null != fragmentRead) {
            fragmentRead = null;
        }
        if (null != fragmentPort) {
            fragmentPort = null;
        }
        if (null != fragmentUser) {
            fragmentUser = null;
        }
    }
}
