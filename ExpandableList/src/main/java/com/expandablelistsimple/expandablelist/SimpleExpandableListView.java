package com.expandablelistsimple.expandablelist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * by y on 2016/11/21
 */

public class SimpleExpandableListView extends ExpandableListView {

    public SimpleExpandableListView(Context context) {
        super(context);
    }

    public SimpleExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
