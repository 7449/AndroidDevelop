package github.com.superadapter.superAdapter;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import github.com.superadapter.R;

/**
 * by y on 2016/9/29
 */

public class RefreshHeaderLayout extends LinearLayout {
    private TextView textView;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADING = 2;
    public static final int STATE_LOAD_ERROR = 3;
    public static final int STATE_SUCCESS = 4;

    private int mStates = STATE_NORMAL;
    public int mMeasuredHeight;
    private View headerView;
    private ImageView imageView;

    public RefreshHeaderLayout(Context context) {
        super(context);
        init();
    }

    public RefreshHeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("InflateParams")
    private void init() {
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.header_layout, null);
        setGravity(Gravity.CENTER);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        textView = (TextView) headerView.findViewById(R.id.tv_header_title);
        imageView = (ImageView) headerView.findViewById(R.id.iv_header_image);

        addView(headerView, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    public void setVisibleHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        LayoutParams layoutParams = (LayoutParams) headerView.getLayoutParams();
        layoutParams.height = height;
        headerView.setLayoutParams(layoutParams);
    }

    public int getVisibleHeight() {
        LayoutParams layoutParams = (LayoutParams) headerView.getLayoutParams();
        return layoutParams.height;
    }

    public void refreshUI(float height) {
        if (getVisibleHeight() > 0 || height > 0) {
            setVisibleHeight((int) (height + getVisibleHeight()));
            if (mStates < STATE_LOADING) {
                if (getVisibleHeight() > mMeasuredHeight) {
                    setStates(STATE_REFRESH);
                } else {
                    setStates(STATE_NORMAL);
                }
            }
        }
    }

    public int getStates() {
        return mStates;
    }

    public void setStates(int status) {
        if (mStates == status) {
            return;
        }
        mStates = status;
        switch (status) {
            case STATE_NORMAL:
                textView.setText("下拉刷新");
                break;
            case STATE_LOADING:
                textView.setText("正在加载中...");
                break;
            case STATE_LOAD_ERROR:
                textView.setText("加载失败");
                break;
            case STATE_REFRESH:
                textView.setText("释放刷新");
                break;
            default:
                textView.setText("加载成功");
                break;
        }
    }

    public boolean releaseAction() {
        boolean isRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) {
            isRefresh = false;
        }
        if (getVisibleHeight() > mMeasuredHeight && mStates == STATE_REFRESH) {
            setStates(STATE_LOADING);
            isRefresh = true;
        }
        int destHeight = 0;
        if (mStates == STATE_LOADING) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);
        return isRefresh;
    }

    public void refreshComplete(int type) {
        setStates(type);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                reset();
            }
        }, 300);
    }

    public void reset() {
        smoothScrollTo(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setStates(STATE_NORMAL);
            }
        }, 100);
    }

    private void smoothScrollTo(int height) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), height);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }
}
