package com.readlist.news.widget;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.readlist.R;

import framework.base.BaseFragment;
import framework.utils.UIUtils;

/**
 * by y on 2016/11/9
 */

public class NewsTabFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    public static NewsTabFragment newInstance() {
        return new NewsTabFragment();
    }

    @Override
    protected void toolbarOnclick() {

    }

    @Override
    protected void initById() {
        tabLayout = getView(R.id.tab_layout);
        viewPager = getView(R.id.viewPager);
    }

    @Override
    protected void initData() {
        viewPager.setAdapter(new TabAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initBundle(Bundle bundle) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    public class TabAdapter extends FragmentPagerAdapter {

        private String[] name;

        public TabAdapter(FragmentManager fm) {
            super(fm);
            name = UIUtils.getStringArray(R.array.news_array);
        }

        @Override
        public Fragment getItem(int position) {
            return NewsListFragment.newInstance(UIUtils.getStringArray(R.array.news_array_suffix)[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return name[position];
        }

        @Override
        public int getCount() {
            return name.length;
        }
    }
}
