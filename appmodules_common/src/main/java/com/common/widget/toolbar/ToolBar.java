package com.common.widget.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.common.R;

import io.reactivex.annotations.NonNull;


public class ToolBar extends FrameLayout {


    private AppCompatImageView leftIv;
    private AppCompatImageView centerIv;
    private AppCompatImageView rightIv;
    private AppCompatTextView centerTv;
    private AppCompatTextView leftTv;
    private AppCompatTextView rightTv;
    private LinearLayout rootView;

    private FrameLayout leftRootView;
    private FrameLayout centerRootView;
    private FrameLayout rightRootView;

    private OnToolBarClickListener listener;

    public ToolBar(@NonNull Context context) {
        super(context);
        init();
    }

    public ToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Context context = getContext();
        View inflate = LinearLayout.inflate(context, R.layout.layout_toolbar, null);
        rootView = inflate.findViewById(R.id.toolbar_root_view);
        leftRootView = inflate.findViewById(R.id.left_root_view);
        centerRootView = inflate.findViewById(R.id.center_root_view);
        rightRootView = inflate.findViewById(R.id.right_root_view);
        leftIv = inflate.findViewById(R.id.left_iv);
        centerIv = inflate.findViewById(R.id.center_iv);
        rightIv = inflate.findViewById(R.id.right_iv);
        leftTv = inflate.findViewById(R.id.left_tv);
        centerTv = inflate.findViewById(R.id.center_tv);
        rightTv = inflate.findViewById(R.id.right_tv);
        addView(inflate);
        leftIv.setOnClickListener(v -> {
            if (listener != null) listener.onLeftClick();
        });
        leftTv.setOnClickListener(v -> {
            if (listener != null) listener.onLeftClick();
        });
        centerIv.setOnClickListener(v -> {
            if (listener != null) listener.onCenterClick();
        });
        centerTv.setOnClickListener(v -> {
            if (listener != null) listener.onCenterClick();
        });
        rightIv.setOnClickListener(v -> {
            if (listener != null) listener.onRightClick();
        });
        rightTv.setOnClickListener(v -> {
            if (listener != null) listener.onRightClick();
        });
    }

    public void setListener(OnToolBarClickListener listener) {
        this.listener = listener;
    }

    public void setBackgroundColor(@ColorInt int id) {
        getToolBarRootView().setBackgroundColor(id);
    }

    public void setLeftIvIcon(@DrawableRes int id) {
        leftIv.setImageResource(id);
    }

    public void setLeftTvText(@StringRes int id) {
        leftTv.setText(id);
    }

    public void setLeftTvTextColor(@ColorInt int id) {
        leftTv.setTextColor(id);
    }

    public void setLeftTvTextSize(float id) {
        leftTv.setTextSize(id);
    }

    public void setLeftTvMargin(float size) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) leftTv.getLayoutParams();
        layoutParams.leftMargin = (int) size;
        leftTv.setLayoutParams(layoutParams);
    }

    public void setCenterIvIcon(@DrawableRes int id) {
        centerIv.setImageResource(id);
    }

    public void setCenterTvText(@StringRes int id) {
        centerTv.setText(id);
    }

    public void setCenterTvTextColor(@ColorInt int id) {
        centerTv.setTextColor(id);
    }

    public void setCenterTvTextSize(float id) {
        centerTv.setTextSize(id);
    }

    public void setRightIvIcon(@DrawableRes int id) {
        rightIv.setImageResource(id);
    }

    public void setRightTvText(@StringRes int id) {
        rightTv.setText(id);
    }

    public void setRightTvTextColor(@ColorInt int id) {
        rightTv.setTextColor(id);
    }

    public void setRightTvTextSize(float id) {
        rightTv.setTextSize(id);
    }

    public AppCompatImageView getLeftIv() {
        return leftIv;
    }

    public AppCompatImageView getCenterIv() {
        return centerIv;
    }

    public AppCompatImageView getRightIv() {
        return rightIv;
    }

    public AppCompatTextView getCenterTv() {
        return centerTv;
    }

    public AppCompatTextView getLeftTv() {
        return leftTv;
    }

    public AppCompatTextView getRightTv() {
        return rightTv;
    }

    public LinearLayout getToolBarRootView() {
        return rootView;
    }

    public FrameLayout getLeftRootView() {
        return leftRootView;
    }

    public FrameLayout getCenterRootView() {
        return centerRootView;
    }

    public FrameLayout getRightRootView() {
        return rightRootView;
    }
}
