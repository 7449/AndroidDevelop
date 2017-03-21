package com.jsoupsimple.tab;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jsoupsimple.R;
import com.jsoupsimple.image.imagelist.widget.ImageFragment;

import framework.data.Constant;
import framework.utils.UIUtils;

/**
 * by y on 2016/9/26.
 */

public class TabAdapter extends FragmentPagerAdapter {

    private String[] name;
    private String type;

    public TabAdapter(FragmentManager childFragmentManager, String type) {
        super(childFragmentManager);
        this.type = type;
        switch (type) {
            case Constant.DOU_BAN_MEI_ZI:
                name = UIUtils.getStringArray(R.array.dbmz_array);
                break;
            case Constant.M_ZI_TU:
                name = UIUtils.getStringArray(R.array.mzitu_array);
                break;
        }

    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(position, type);
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
