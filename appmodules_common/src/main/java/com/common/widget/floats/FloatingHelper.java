package com.common.widget.floats;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.common.R;
import com.common.util.UIUtils;

public class FloatingHelper {
    private FloatLayout floatLayout;

    public FloatingHelper(View rootView, View floatView) {
        floatLayout = new FloatLayout(rootView.getContext());
        FrameLayout frameLayout = UIUtils.getView(R.layout.layout_float);
        frameLayout.addView(floatView);
        floatLayout.addView(rootView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        floatLayout.addHelperView(frameLayout, new FrameLayout.LayoutParams(UIUtils.dip2px(30), UIUtils.dip2px(30)));
    }

    public FloatingHelper(View rootView, View floatView, FrameLayout.LayoutParams layoutParams) {
        floatLayout = new FloatLayout(rootView.getContext());
        FrameLayout frameLayout = UIUtils.getView(R.layout.layout_float);
        frameLayout.addView(floatView);
        floatLayout.addView(rootView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        floatLayout.addHelperView(frameLayout, layoutParams);
    }

    public FloatingHelper(int rootViewId, int floatViewId) {
        View rootView = UIUtils.getView(rootViewId);
        View floatView = UIUtils.getView(floatViewId);
        floatLayout = new FloatLayout(rootView.getContext());
        FrameLayout frameLayout = UIUtils.getView(R.layout.layout_float);
        frameLayout.addView(floatView);
        floatLayout.addView(rootView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        floatLayout.addHelperView(frameLayout, new FrameLayout.LayoutParams(UIUtils.dip2px(30), UIUtils.dip2px(30)));
    }

    public FloatingHelper(int rootViewId, int floatViewId, FrameLayout.LayoutParams layoutParams) {
        View rootView = UIUtils.getView(rootViewId);
        View floatView = UIUtils.getView(floatViewId);
        floatLayout = new FloatLayout(rootView.getContext());
        FrameLayout frameLayout = UIUtils.getView(R.layout.layout_float);
        frameLayout.addView(floatView);
        floatLayout.addView(rootView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        floatLayout.addHelperView(frameLayout, layoutParams);
    }

    public View getView() {
        return floatLayout;
    }
}
