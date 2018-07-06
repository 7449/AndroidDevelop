package com.common.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * by y on 2016/10/28
 */

public class ViewPagerCompat extends ViewPager {

    private boolean mViewTouchMode = false;

    public ViewPagerCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewTouchMode(boolean b) {
        if (b && !isFakeDragging()) {
            beginFakeDrag();
        } else if (!b && isFakeDragging()) {
            endFakeDrag();
        }
        mViewTouchMode = b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return !mViewTouchMode && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean arrowScroll(int direction) {
        return !mViewTouchMode && (direction == FOCUS_LEFT || direction == FOCUS_RIGHT) && super.arrowScroll(direction);
    }

}
