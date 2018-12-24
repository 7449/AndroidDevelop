package xyz.viseator.anonymouscard.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by viseator on 2016/12/20.
 * Wudi
 * viseator@gmail.com
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragments) {
        super(fm);
        this.context = context;
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
