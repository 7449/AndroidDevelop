package github.com.viewpagerdemo.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import java.util.List;

/**
 * by y on 2016/9/28
 */

public interface BannerInterface<T> {

    Context getContext();

    ViewPager getViewPager();

    LinearLayout getLinearLayout();

    BasePagerAdapter getBannerAdapter(List<T> banner);
}
