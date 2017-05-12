package com.auxiliary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.auxiliary.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * by y on 2017/5/12
 */

public class StatusLayout extends FrameLayout {
    public static final int LOADING = 1;
    public static final int SUCCESS = 2;
    public static final int ERROR = 3;
    public static final int EMPTY = 4;

    @IntDef({
            StatusLayout.LOADING,
            StatusLayout.SUCCESS,
            StatusLayout.EMPTY,
            StatusLayout.ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusMode {
    }


    private int mStatus = SUCCESS;

    private View loadView;
    private View errorView;
    private View emptyView;
    private View successView;

    private static final int NO_LAYOUT = 0X00;

    public View getViewLayout(@LayoutRes int id) {
        return LayoutInflater.from(getContext()).inflate(id, this, false);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StatusLayout);

        int loadViewId = typedArray.getResourceId(R.styleable.StatusLayout_status_loading_layout, NO_LAYOUT);
        int errorViewId = typedArray.getResourceId(R.styleable.StatusLayout_status_error_layout, NO_LAYOUT);
        int emptyViewId = typedArray.getResourceId(R.styleable.StatusLayout_status_empty_layout, NO_LAYOUT);
        int successViewId = typedArray.getResourceId(R.styleable.StatusLayout_status_success_layout, NO_LAYOUT);


        if (loadViewId != NO_LAYOUT) {
            setLoadView(loadViewId);
        }
        if (errorViewId != NO_LAYOUT) {
            setErrorView(errorViewId);

        }
        if (emptyViewId != NO_LAYOUT) {
            setEmptyView(emptyViewId);
        }
        if (successViewId != NO_LAYOUT) {
            setSuccessView(successViewId);
        }

        typedArray.recycle();
    }

    public StatusLayout(Context context) {
        super(context);
        init(null);
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    public int getStatus() {
        return mStatus;
    }

    public void setLoadView(@NonNull View loadView) {
        this.loadView = loadView;
        addView(loadView, getParams());
    }

    public void setLoadView(@LayoutRes int loadViewId) {
        this.loadView = getViewLayout(loadViewId);
        addView(loadView, getParams());
    }

    public void setLoadView(@NonNull View loadView, @NonNull FrameLayout.LayoutParams params) {
        this.loadView = loadView;
        addView(loadView, params);
    }

    public void setErrorView(@NonNull View errorView) {
        this.errorView = errorView;
        addView(errorView, getParams());
    }

    public void setErrorView(@LayoutRes int errorViewId) {
        this.errorView = getViewLayout(errorViewId);
        addView(errorView, getParams());
    }

    public void setErrorView(@NonNull View errorView, @NonNull LayoutParams params) {
        this.errorView = errorView;
        addView(errorView, params);
    }

    public void setEmptyView(@NonNull View emptyView) {
        this.emptyView = emptyView;
        addView(emptyView, getParams());
    }

    public void setEmptyView(@LayoutRes int emptyViewId) {
        this.emptyView = getViewLayout(emptyViewId);
        addView(emptyView, getParams());
    }

    public void setEmptyView(@NonNull View emptyView, @NonNull LayoutParams params) {
        this.emptyView = emptyView;
        addView(emptyView, params);
    }

    public void setSuccessView(@NonNull View successView) {
        this.successView = successView;
        addView(successView, getParams());
    }

    public void setSuccessView(@LayoutRes int successViewId) {
        this.successView = getViewLayout(successViewId);
        addView(successView, getParams());
    }

    public void setSuccessView(@NonNull View successView, @NonNull LayoutParams params) {
        this.successView = successView;
        addView(successView, params);
    }

    public View getLoadView() {
        return loadView;
    }

    public View getErrorView() {
        return errorView;
    }

    public View getEmptyView() {
        return emptyView;
    }

    public View getSuccessView() {
        return successView;
    }

    public void setStatus(@StatusMode int status) {
        if (mStatus == status || loadView == null || errorView == null || emptyView == null || successView == null) {
            return;
        }
        mStatus = status;
        switch (status) {
            case LOADING:
                loadView.setVisibility(VISIBLE);
                errorView.setVisibility(GONE);
                emptyView.setVisibility(GONE);
                successView.setVisibility(GONE);
                break;
            case ERROR:
                errorView.setVisibility(VISIBLE);
                loadView.setVisibility(GONE);
                emptyView.setVisibility(GONE);
                successView.setVisibility(GONE);
                break;
            case EMPTY:
                emptyView.setVisibility(VISIBLE);
                loadView.setVisibility(GONE);
                errorView.setVisibility(GONE);
                successView.setVisibility(GONE);
                break;
            case SUCCESS:
                successView.setVisibility(VISIBLE);
                loadView.setVisibility(GONE);
                errorView.setVisibility(GONE);
                emptyView.setVisibility(GONE);
                break;
        }
    }


    private LayoutParams getParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    }
}
