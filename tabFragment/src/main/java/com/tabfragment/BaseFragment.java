package com.tabfragment;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;

/**
 * by y on 2016/10/5.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //避免横竖屏切换时页面重叠，对应的activity添加android:configChanges = "orientation|screenSize"
    }
}
