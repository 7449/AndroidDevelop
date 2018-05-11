package com.superadapter.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.superadapter.R;


/**
 * by y on 2016/9/29
 */

public class LoadMoreFooterLayout extends LinearLayout {

    private TextView textView;
    public static final int STATE_LOADING = 1;
    public static final int STATE_LOAD_EMPTY = 2;
    public static final int STATE_LOAD_ERROR = 3;
    public static final int STATE_SUCCESS = 4;

    private int mStatus = -1;

    public LoadMoreFooterLayout(Context context) {
        super(context);
        init();
    }

    public LoadMoreFooterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreFooterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView = new TextView(getContext());
        textView.setText("上拉加载数据...");
        textView.setPadding(10, 10, 10, 10);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setCompoundDrawablePadding(20);
        setImage(R.mipmap.ic_launcher);
        addView(textView);
    }

    public void loadMoreComplete(int type) {
        setStatus(type);
    }

    public void setStatus(int status) {
        if (null == textView || mStatus == status) {
            return;
        }
        mStatus = status;
        switch (status) {
            case STATE_LOAD_EMPTY:
                textView.setText("没有更多数据了");
                break;
            case STATE_LOADING:
                textView.setText("正在加载中...");
                break;
            case STATE_LOAD_ERROR:
                textView.setText("加载失败");
                break;
            default:
                textView.setText("加载成功");
                break;
        }
    }

    public void setImage(int id) {
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }


}
