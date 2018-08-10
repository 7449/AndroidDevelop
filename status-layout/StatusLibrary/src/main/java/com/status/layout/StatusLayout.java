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
    private String mStatus;
    private OnStatusClickListener onStatusClickListener;
    private View mNorMalView;
    private View mLoadingView;
    private View mEmptyView;
    private View mSuccessView;
    private View mErrorView;

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
        typedArray.recycle();
        if (mNormalLayoutId != NO_ID) {
            addNorMalView(mNormalLayoutId);
        }
        if (mLoadingLayoutId != NO_ID) {
            addLoadingView(mLoadingLayoutId);
        }
        if (mEmptyLayoutId != NO_ID) {
            addEmptyView(mEmptyLayoutId);
        }
        if (mSuccessLayoutId != NO_ID) {
            addSuccessView(mSuccessLayoutId);
        }
        if (mErrorLayoutId != NO_ID) {
            addErrorView(mErrorLayoutId);
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

    public StatusLayout addNorMalView(@NonNull View norMalView) {
        addNorMalView(norMalView, Util.getParams());
        return this;
    }

    public StatusLayout addLoadingView(@NonNull View loadingView) {
        addLoadingView(loadingView, Util.getParams());
        return this;
    }

    public StatusLayout addEmptyView(@NonNull View emptyView) {
        addEmptyView(emptyView, Util.getParams());
        return this;
    }

    public StatusLayout addSuccessView(@NonNull View successView) {
        addSuccessView(successView, Util.getParams());
        return this;
    }

    public StatusLayout addErrorView(@NonNull View errorView) {
        addErrorView(errorView, Util.getParams());
        return this;
    }

    public StatusLayout addNorMalView(@LayoutRes int normalLayoutRes) {
        addNorMalView(Util.getViewLayout(this, normalLayoutRes));
        return this;
    }

    public StatusLayout addLoadingView(@LayoutRes int loadingLayoutRes) {
        addLoadingView(Util.getViewLayout(this, loadingLayoutRes));
        return this;
    }

    public StatusLayout addEmptyView(@LayoutRes int emptyLayoutRes) {
        addEmptyView(Util.getViewLayout(this, emptyLayoutRes));
        return this;
    }

    public StatusLayout addSuccessView(@LayoutRes int successLayoutRes) {
        addSuccessView(Util.getViewLayout(this, successLayoutRes));
        return this;
    }

    public StatusLayout addErrorView(@LayoutRes int errorLayoutRes) {
        addErrorView(Util.getViewLayout(this, errorLayoutRes));
        return this;
    }

    public StatusLayout addNorMalView(@LayoutRes int normalLayoutRes, @Nullable LayoutParams params) {
        addNorMalView(Util.getViewLayout(this, normalLayoutRes), params);
        return this;
    }

    public StatusLayout addLoadingView(@LayoutRes int loadingLayoutRes, @Nullable LayoutParams params) {
        addLoadingView(Util.getViewLayout(this, loadingLayoutRes), params);
        return this;
    }

    public StatusLayout addEmptyView(@LayoutRes int emptyLayoutRes, @Nullable LayoutParams params) {
        addEmptyView(Util.getViewLayout(this, emptyLayoutRes), params);
        return this;
    }

    public StatusLayout addSuccessView(@LayoutRes int successLayoutRes, @Nullable LayoutParams params) {
        addSuccessView(Util.getViewLayout(this, successLayoutRes), params);
        return this;
    }

    public StatusLayout addErrorView(@LayoutRes int errorLayoutRes, @Nullable LayoutParams params) {
        addErrorView(Util.getViewLayout(this, errorLayoutRes), params);
        return this;
    }

    public StatusLayout addNorMalView(@NonNull View norMalView, @Nullable LayoutParams params) {
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

    public StatusLayout addLoadingView(@NonNull View loadingView, @Nullable LayoutParams params) {
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

    public StatusLayout addEmptyView(@NonNull View emptyView, @Nullable LayoutParams params) {
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

    public StatusLayout addSuccessView(@NonNull View successView, @Nullable LayoutParams params) {
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

    public StatusLayout addErrorView(@NonNull View errorView, @Nullable LayoutParams params) {
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

    public View getView(@Status String status) {
        switch (status) {
            case Status.NORMAL:
                return mNorMalView;
            case Status.LOADING:
                return mLoadingView;
            case Status.EMPTY:
                return mEmptyView;
            case Status.SUCCESS:
                return mSuccessView;
            case Status.ERROR:
                return mErrorView;
            default:
                throw new RuntimeException("please check status");
        }
    }
}

