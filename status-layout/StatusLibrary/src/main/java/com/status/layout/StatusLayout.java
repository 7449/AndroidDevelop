package com.status.layout;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * by y on 14/07/2017.
 */

public class StatusLayout extends FrameLayout {


    private String mStatus = null;
    private OnStatusClickListener onStatusClickListener = null;
    private View mNorMalView = null;
    private View mLoadingView = null;
    private View mEmptyView = null;
    private View mSuccessView = null;
    private View mErrorView = null;

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
        int mNormalLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_normal_layout, NO_ID);
        int mLoadingLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_loading_layout, NO_ID);
        int mEmptyLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_empty_layout, NO_ID);
        int mSuccessLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_success_layout, NO_ID);
        int mErrorLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_error_layout, NO_ID);
        boolean normalFlag = typedArray.getBoolean(R.styleable.StatusLayout_status_normal_flag, false);
        boolean loadingFlag = typedArray.getBoolean(R.styleable.StatusLayout_status_loading_flag, false);
        boolean emptyFlag = typedArray.getBoolean(R.styleable.StatusLayout_status_empty_flag, false);
        boolean successFlag = typedArray.getBoolean(R.styleable.StatusLayout_status_success_flag, false);
        boolean errorFlag = typedArray.getBoolean(R.styleable.StatusLayout_status_error_flag, false);
        typedArray.recycle();

        if (mNormalLayoutId != NO_ID) {
            if (normalFlag) {
                setNorMalView(mNormalLayoutId);
            } else {
                setNorMalView(mNormalLayoutId, null);
            }
        }
        if (mLoadingLayoutId != NO_ID) {
            if (loadingFlag) {
                setLoadingView(mLoadingLayoutId);
            } else {
                setLoadingView(mLoadingLayoutId, null);
            }
        }
        if (mEmptyLayoutId != NO_ID) {
            if (emptyFlag) {
                setEmptyView(mEmptyLayoutId);
            } else {
                setEmptyView(mEmptyLayoutId, null);
            }
        }
        if (mSuccessLayoutId != NO_ID) {
            if (successFlag) {
                setSuccessView(mSuccessLayoutId);
            } else {
                setSuccessView(mSuccessLayoutId, null);
            }
        }
        if (mErrorLayoutId != NO_ID) {
            if (errorFlag) {
                setErrorView(mErrorLayoutId);
            } else {
                setErrorView(mErrorLayoutId, null);
            }
        }
        Util.goneView(mNorMalView, mLoadingView, mEmptyView, mSuccessView, mErrorView);
    }

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

    public void setOnStatusClickListener(OnStatusClickListener clickListener) {
        this.onStatusClickListener = clickListener;
    }


    /**********************   view  默认 params ， 填充屏幕   ************/


    public StatusLayout setNorMalView(@NonNull View norMalView) {
        setNorMalView(norMalView, Util.getParams());
        return this;
    }

    public StatusLayout setLoadingView(@NonNull View loadingView) {
        setLoadingView(loadingView, Util.getParams());
        return this;
    }

    public StatusLayout setEmptyView(@NonNull View emptyView) {
        setEmptyView(emptyView, Util.getParams());
        return this;
    }

    public StatusLayout setSuccessView(@NonNull View successView) {
        setSuccessView(successView, Util.getParams());
        return this;
    }

    public StatusLayout setErrorView(@NonNull View errorView) {
        setErrorView(errorView, Util.getParams());
        return this;
    }

    /**********************   LayoutRes  默认 params ， 填充屏幕并居中   ************/

    public StatusLayout setNorMalView(@LayoutRes int normalLayoutRes) {
        setNorMalView(Util.getViewLayout(this, normalLayoutRes));
        return this;
    }

    public StatusLayout setLoadingView(@LayoutRes int loadingLayoutRes) {
        setLoadingView(Util.getViewLayout(this, loadingLayoutRes));
        return this;
    }

    public StatusLayout setEmptyView(@LayoutRes int emptyLayoutRes) {
        setEmptyView(Util.getViewLayout(this, emptyLayoutRes));
        return this;
    }

    public StatusLayout setSuccessView(@LayoutRes int successLayoutRes) {
        setSuccessView(Util.getViewLayout(this, successLayoutRes));
        return this;
    }

    public StatusLayout setErrorView(@LayoutRes int errorLayoutRes) {
        setErrorView(Util.getViewLayout(this, errorLayoutRes));
        return this;
    }

    /**********************   LayoutRes, params（可为 Null）  ************/

    public StatusLayout setNorMalView(@LayoutRes int normalLayoutRes, @Nullable LayoutParams params) {
        setNorMalView(Util.getViewLayout(this, normalLayoutRes), params);
        return this;
    }

    public StatusLayout setLoadingView(@LayoutRes int loadingLayoutRes, @Nullable LayoutParams params) {
        setLoadingView(Util.getViewLayout(this, loadingLayoutRes), params);
        return this;
    }

    public StatusLayout setEmptyView(@LayoutRes int emptyLayoutRes, @Nullable LayoutParams params) {
        setEmptyView(Util.getViewLayout(this, emptyLayoutRes), params);
        return this;
    }

    public StatusLayout setSuccessView(@LayoutRes int successLayoutRes, @Nullable LayoutParams params) {
        setSuccessView(Util.getViewLayout(this, successLayoutRes), params);
        return this;
    }

    public StatusLayout setErrorView(@LayoutRes int errorLayoutRes, @Nullable LayoutParams params) {
        setErrorView(Util.getViewLayout(this, errorLayoutRes), params);
        return this;
    }

    /**********************   最终填充View方法  flag addView() 的时候是否 使用 params   ************/

    public StatusLayout setNorMalView(@NonNull View norMalView, @Nullable LayoutParams params) {
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
            if (onStatusClickListener != null)
                onStatusClickListener.onNorMalClick(v);
        });
        return this;
    }

    public StatusLayout setLoadingView(@NonNull View loadingView, @Nullable LayoutParams params) {
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
            if (onStatusClickListener != null)
                onStatusClickListener.onLoadingClick(v);
        });
        return this;
    }

    public StatusLayout setEmptyView(@NonNull View emptyView, @Nullable LayoutParams params) {
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
            if (onStatusClickListener != null)
                onStatusClickListener.onEmptyClick(v);
        });
        return this;
    }

    public StatusLayout setSuccessView(@NonNull View successView, @Nullable LayoutParams params) {
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
            if (onStatusClickListener != null)
                onStatusClickListener.onSuccessClick(v);
        });
        return this;
    }

    public StatusLayout setErrorView(@NonNull View errorView, @Nullable LayoutParams params) {
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
            if (onStatusClickListener != null)
                onStatusClickListener.onErrorClick(v);
        });
        return this;
    }

    public String getStatus() {
        return mStatus;
    }

    /************   返回View，有可能为 Null   ***********/

    @Nullable
    public View getNorMalView() {
        return mNorMalView;
    }

    @Nullable
    public View getLoadingView() {
        return mLoadingView;
    }

    @Nullable
    public View getEmptyView() {
        return mEmptyView;
    }

    @Nullable
    public View getSuccessView() {
        return mSuccessView;
    }

    @Nullable
    public View getErrorView() {
        return mErrorView;
    }

}

