package com.banner.util;

import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

/**
 * by y on 2016/9/28
 */

public interface BannerInterface {

    ViewPager getViewPager();

    LinearLayout getLinearLayout();

    BasePagerAdapter getBannerAdapter();
}
