package sample.view.develop.android.numberpicker.widget.listener;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import sample.view.develop.android.numberpicker.widget.NumberPickerView;


/**
 * by y on 2017/3/16
 * <p>
 * <p>
 * NumberPickerView 的滚动状态
 */

public interface OnScrollListener {
    int SCROLL_STATE_IDLE = 0;
    int SCROLL_STATE_TOUCH_SCROLL = 1;
    int SCROLL_STATE_FLING = 2;

    void onScrollStateChange(NumberPickerView view, @ScrollMode int scrollState);

    @IntDef({OnScrollListener.SCROLL_STATE_IDLE,
            OnScrollListener.SCROLL_STATE_TOUCH_SCROLL,
            OnScrollListener.SCROLL_STATE_FLING})
    @Retention(RetentionPolicy.SOURCE)
    @interface ScrollMode {
    }
}


