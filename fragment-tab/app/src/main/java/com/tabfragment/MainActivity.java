package com.tabfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

/**
 * 17.06.28 update
 * <p>
 * 接盘的项目 类似的布局使用XAdapter 有问题，怀疑是项目以前遗留的问题，更新项目测试下问题原因
 *
 * DONE!!
 */
public class MainActivity extends AppCompatActivity {

    private Fragment fragmentOne, fragmentTwo, fragmentThree, fragmentFour;

    private static final String FRAGMENT_ONE = "one";
    private static final String FRAGMENT_TWO = "two";
    private static final String FRAGMENT_THREE = "three";
    private static final String FRAGMENT_FOUR = "four";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTabSelect(0);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_one:
                        setTabSelect(0);
                        break;
                    case R.id.rb_two:
                        setTabSelect(1);
                        break;
                    case R.id.rb_three:
                        setTabSelect(2);
                        break;
                    case R.id.rb_four:
                        setTabSelect(3);
                        break;
                }
            }
        });
    }

    private void setTabSelect(int i) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (i) {
            case 0:
                fragmentOne = manager.findFragmentByTag(FRAGMENT_ONE);
                hideTab(transaction);
                if (null == fragmentOne) {
                    fragmentOne = FragmentOne.startFragment();
                    transaction.add(R.id.fragment, fragmentOne, FRAGMENT_ONE);
                } else {
                    transaction.show(fragmentOne);
                }
                break;
            case 1:
                fragmentTwo = manager.findFragmentByTag(FRAGMENT_TWO);
                hideTab(transaction);
                if (null == fragmentTwo) {
                    fragmentTwo = FragmentTwo.startFragment();
                    transaction.add(R.id.fragment, fragmentTwo, FRAGMENT_TWO);
                } else {
                    transaction.show(fragmentTwo);
                }
                break;
            case 2:
                fragmentThree = manager.findFragmentByTag(FRAGMENT_THREE);
                hideTab(transaction);
                if (null == fragmentThree) {
                    fragmentThree = FragmentThree.startFragment();
                    transaction.add(R.id.fragment, fragmentThree, FRAGMENT_THREE);
                } else {
                    transaction.show(fragmentThree);
                }
                break;
            case 3:
                fragmentFour = manager.findFragmentByTag(FRAGMENT_FOUR);
                hideTab(transaction);
                if (null == fragmentFour) {
                    fragmentFour = FragmentFour.startFragment();
                    transaction.add(R.id.fragment, fragmentFour, FRAGMENT_FOUR);
                } else {
                    transaction.show(fragmentFour);
                }
                break;
        }

        transaction.commit();
    }


    private void hideTab(FragmentTransaction transaction) {
        if (null != fragmentOne) {
            transaction.hide(fragmentOne);
        }
        if (null != fragmentTwo) {
            transaction.hide(fragmentTwo);
        }
        if (null != fragmentThree) {
            transaction.hide(fragmentThree);
        }
        if (null != fragmentFour) {
            transaction.hide(fragmentFour);
        }
    }

}

