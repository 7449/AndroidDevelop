package com.common.widget.status;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


import com.common.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 14/07/2017.
 * <p>
 * 多种状态布局Layout
 * <p>
 * 不一定要把所有的状态View都填充，例如有些页面没必要设置 ErrorView，可以不用填充
 * <p>
 * 建议 在 BaseActivity 或者 BaseFragment 里面  设置 StatusLayout 为 RootView，这样想改变页面状态很简单就完成了
 * <p>
 * <p>
 * 使用示例：
 * <pre>
 *     <StatusLayout
 *            android:id="@+id/statusLayout"
 *            android:layout_width="match_parent"
 *            android:layout_height="300dp"
 *            app:status_empty_layout="@layout/layout_empty"
 *            app:status_error_layout="@layout/layout_error"
 *            app:status_loading_layout="@layout/layout_loading"
 *            app:status_normal_layout="@layout/layout_normal"
 *            app:status_success_layout="@layout/layout_success" />
 *
 * or
 *
 *     statusLayout.setNorMalView(R.layout.layout_normal, null);
 *     statusLayout.setLoadingView(R.layout.layout_loading, null);
 *     statusLayout.setEmptyView(R.layout.layout_empty, null);
 *     statusLayout.setSuccessView(R.layout.layout_success, null);
 *     statusLayout.setErrorView(R.layout.layout_error, null);
 */

public class StatusLayout extends FrameLayout {

    private static final int NO_LAYOUT = 0x00;

    private String mStatus = null;
    private StatusClickListener clickListener = null;
    private View mNorMalView = null;
    private View mLoadingView = null;
    private View mEmptyView = null;
    private View mSuccessView = null;
    private View mErrorView = null;
    @LayoutRes
    private int mNormalLayoutId = NO_LAYOUT;
    @LayoutRes
    private int mLoadingLayoutId = NO_LAYOUT;
    @LayoutRes
    private int mEmptyLayoutId = NO_LAYOUT;
    @LayoutRes
    private int mSuccessLayoutId = NO_LAYOUT;
    @LayoutRes
    private int mErrorLayoutId = NO_LAYOUT;
    public StatusLayout(@NonNull Context context) {
        super(context);
        init(null);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StatusLayout);

        mNormalLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_normal_layout, NO_LAYOUT);
        mLoadingLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_loading_layout, NO_LAYOUT);
        mEmptyLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_empty_layout, NO_LAYOUT);
        mSuccessLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_success_layout, NO_LAYOUT);
        mErrorLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_error_layout, NO_LAYOUT);


        boolean normalFlag = typedArray.getBoolean(R.styleable.StatusLayout_status_normal_flag, false);
        boolean loadingFlag = typedArray.getBoolean(R.styleable.StatusLayout_status_loading_flag, false);
        boolean emptyFlag = typedArray.getBoolean(R.styleable.StatusLayout_status_empty_flag, false);
        boolean successFlag = typedArray.getBoolean(R.styleable.StatusLayout_status_success_flag, false);
        boolean errorFlag = typedArray.getBoolean(R.styleable.StatusLayout_status_error_flag, false);

        if (mNormalLayoutId != NO_LAYOUT) {
            if (normalFlag) {
                setNorMalView(mNormalLayoutId);
            } else {
                setNorMalView(mNormalLayoutId, null);
            }
        }
        if (mLoadingLayoutId != NO_LAYOUT) {
            if (loadingFlag) {
                setLoadingView(mLoadingLayoutId);
            } else {
                setLoadingView(mLoadingLayoutId, null);
            }
        }
        if (mEmptyLayoutId != NO_LAYOUT) {
            if (emptyFlag) {
                setEmptyView(mEmptyLayoutId);
            } else {
                setEmptyView(mEmptyLayoutId, null);
            }
        }
        if (mSuccessLayoutId != NO_LAYOUT) {
            if (successFlag) {
                setSuccessView(mSuccessLayoutId);
            } else {
                setSuccessView(mSuccessLayoutId, null);
            }
        }
        if (mErrorLayoutId != NO_LAYOUT) {
            if (errorFlag) {
                setErrorView(mErrorLayoutId);
            } else {
                setErrorView(mErrorLayoutId, null);
            }
        }

        typedArray.recycle();

        Util.goneView(mNorMalView, mLoadingView, mEmptyView, mSuccessView, mErrorView);
    }

    /**
     * 改变布局的状态
     *
     * @param status see{@link Status}
     * @return false : 当前状态和设置的状态一致.
     */
    public boolean setStatus(@Status String status) {
        if (TextUtils.equals(mStatus, status)) {
            return false;
        }
        Util.goneView(mNorMalView, mLoadingView, mEmptyView, mSuccessView, mErrorView);
        switch (status) {
            case Status.NORMAL:
                Util.visibilityView(mNorMalView);
                break;
            case Status.LOADING:
                Util.visibilityView(mLoadingView);
                break;
            case Status.EMPTY:
                Util.visibilityView(mEmptyView);
                break;
            case Status.SUCCESS:
                Util.visibilityView(mSuccessView);
                break;
            case Status.ERROR:
                Util.visibilityView(mErrorView);
                break;
            default:
                throw new RuntimeException("please check status");
        }
        mStatus = status;
        return true;
    }

    public void setStatusClickListener(StatusClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**********************   LayoutRes  默认 params ， 填充屏幕并居中   ************/

    public void setNorMalView(@LayoutRes int normalLayoutRes) {
        setNorMalView(Util.getViewLayout(this, normalLayoutRes));
    }

    public void setLoadingView(@LayoutRes int loadingLayoutRes) {
        setLoadingView(Util.getViewLayout(this, loadingLayoutRes));
    }

    public void setEmptyView(@LayoutRes int emptyLayoutRes) {
        setEmptyView(Util.getViewLayout(this, emptyLayoutRes));
    }

    public void setSuccessView(@LayoutRes int successLayoutRes) {
        setSuccessView(Util.getViewLayout(this, successLayoutRes));
    }

    public void setErrorView(@LayoutRes int errorLayoutRes) {
        setErrorView(Util.getViewLayout(this, errorLayoutRes));
    }

    /**********************   LayoutRes, params（可为 Null）  ************/

    public void setNorMalView(@LayoutRes int normalLayoutRes, @Nullable LayoutParams params) {
        setNorMalView(Util.getViewLayout(this, normalLayoutRes), params);
    }

    public void setLoadingView(@LayoutRes int loadingLayoutRes, @Nullable LayoutParams params) {
        setLoadingView(Util.getViewLayout(this, loadingLayoutRes), params);
    }

    public void setEmptyView(@LayoutRes int emptyLayoutRes, @Nullable LayoutParams params) {
        setEmptyView(Util.getViewLayout(this, emptyLayoutRes), params);
    }

    public void setSuccessView(@LayoutRes int successLayoutRes, @Nullable LayoutParams params) {
        setSuccessView(Util.getViewLayout(this, successLayoutRes), params);
    }

    public void setErrorView(@LayoutRes int errorLayoutRes, @Nullable LayoutParams params) {
        setErrorView(Util.getViewLayout(this, errorLayoutRes), params);
    }

    /**********************   最终填充View方法  flag addView() 的时候是否 使用 params   ************/

    public void setNorMalView(@NonNull View norMalView, @Nullable LayoutParams params) {
        if (mNorMalView != null) {
            removeView(mNorMalView);
            mNorMalView = null;
        }
        this.mNorMalView = norMalView;
        if (params != null) {
            addView(norMalView, params);
        } else {
            addView(norMalView);
        }
        mNorMalView.setOnClickListener(v -> {
            if (clickListener != null)
                clickListener.onNorMalClick();
        });
    }

    public void setLoadingView(@NonNull View loadingView, @Nullable LayoutParams params) {
        if (mLoadingView != null) {
            removeView(mLoadingView);
            mLoadingView = null;
        }
        this.mLoadingView = loadingView;
        if (params != null) {
            addView(loadingView, params);
        } else {
            addView(loadingView);
        }
        mLoadingView.setOnClickListener(v -> {
            if (clickListener != null)
                clickListener.onLoadingClick();
        });
    }

    public void setEmptyView(@NonNull View emptyView, @Nullable LayoutParams params) {
        if (mEmptyView != null) {
            removeView(mEmptyView);
            mEmptyView = null;
        }
        this.mEmptyView = emptyView;
        if (params != null) {
            addView(emptyView, params);
        } else {
            addView(emptyView);
        }
        mEmptyView.setOnClickListener(v -> {
            if (clickListener != null)
                clickListener.onEmptyClick();
        });
    }

    public void setSuccessView(@NonNull View successView, @Nullable LayoutParams params) {
        if (mSuccessView != null) {
            removeView(mSuccessView);
            mSuccessView = null;
        }
        this.mSuccessView = successView;
        if (params != null) {
            addView(successView, params);
        } else {
            addView(successView);
        }
        mSuccessView.setOnClickListener(v -> {
            if (clickListener != null)
                clickListener.onSuccessClick();
        });
    }

    public void setErrorView(@NonNull View errorView, @Nullable LayoutParams params) {
        if (mErrorView != null) {
            removeView(mErrorView);
            mErrorView = null;
        }
        this.mErrorView = errorView;
        if (params != null) {
            addView(errorView, params);
        } else {
            addView(errorView);
        }
        mErrorView.setOnClickListener(v -> {
            if (clickListener != null)
                clickListener.onErrorClick();
        });
    }

    public String getStatus() {
        return mStatus;
    }

    /************   返回View，有可能为 Null   ***********/

    @Nullable
    public View getNorMalView() {
        return mNorMalView;
    }

    /**********************   view  默认 params ， 填充屏幕   ************/


    public void setNorMalView(@NonNull View norMalView) {
        setNorMalView(norMalView, Util.getParams());
    }

    @Nullable
    public View getLoadingView() {
        return mLoadingView;
    }

    public void setLoadingView(@NonNull View loadingView) {
        setLoadingView(loadingView, Util.getParams());
    }

    @Nullable
    public View getEmptyView() {
        return mEmptyView;
    }

    public void setEmptyView(@NonNull View emptyView) {
        setEmptyView(emptyView, Util.getParams());
    }

    @Nullable
    public View getSuccessView() {
        return mSuccessView;
    }

    public void setSuccessView(@NonNull View successView) {
        setSuccessView(successView, Util.getParams());
    }

    @Nullable
    public View getErrorView() {
        return mErrorView;
    }

    public void setErrorView(@NonNull View errorView) {
        setErrorView(errorView, Util.getParams());
    }

    @StringDef({
            Status.NORMAL,
            Status.LOADING,
            Status.EMPTY,
            Status.SUCCESS,
            Status.ERROR
    })
    @Retention(RetentionPolicy.SOURCE)

    public @interface Status {
        String NORMAL = "StatusLayout:Normal"; // 初始状态
        String LOADING = "StatusLayout:Loading"; // 正在加载
        String EMPTY = "StatusLayout:Empty"; // 加载空布局
        String SUCCESS = "StatusLayout:Success"; // 加载成功
        String ERROR = "StatusLayout:Error"; // 加载失败
    }

}

