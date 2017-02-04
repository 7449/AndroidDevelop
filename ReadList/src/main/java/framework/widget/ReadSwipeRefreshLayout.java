package framework.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * by y on 2016/11/8
 */

public class ReadSwipeRefreshLayout extends SwipeRefreshLayout {
    public ReadSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ReadSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(final OnRefreshListener onRefreshListener) {
        this.setOnRefreshListener(onRefreshListener);
        this.post(new Runnable() {
            @Override
            public void run() {
                onRefreshListener.onRefresh();
            }
        });
    }
}
