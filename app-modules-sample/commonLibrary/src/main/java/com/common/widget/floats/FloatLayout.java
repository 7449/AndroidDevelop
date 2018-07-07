package com.common.widget.floats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


public class FloatLayout extends FrameLayout {
    public static final String KEY_FLOAT_X = "KEY_FLOAT_X";
    public static final String KEY_FLOAT_Y = "KEY_FLOAT_Y";

    private ViewDragHelper dragHelper;
    private SharedPreferences sp = getContext().getSharedPreferences(FloatLayout.class.getSimpleName(), Context.MODE_PRIVATE);
    private SharedPreferences.Editor editor = sp.edit();
    private View helperView;

    public FloatLayout(Context context) {
        super(context);
        init();
    }

    public FloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void addHelperView(View helperView) {
        removeView(helperView);
        this.helperView = helperView;
        addView(helperView);
    }


    public void addHelperView(View helperView, LayoutParams layoutParams) {
        removeView(helperView);
        this.helperView = helperView;
        addView(helperView, layoutParams);
    }

    public void addHelperView(View helperView, int width, int height) {
        removeView(helperView);
        this.helperView = helperView;
        addView(helperView, width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        restorePosition();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, 1.0f, new FloatCallback());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        savePosition();
    }

    private void savePosition() {
        if (helperView == null) return;
        float x = helperView.getX();
        float y = helperView.getY();
        editor.putFloat(KEY_FLOAT_X, x);
        editor.putFloat(KEY_FLOAT_Y, y);
        editor.apply();
    }

    private void restorePosition() {
        if (helperView == null) return;
        float x = sp.getFloat(KEY_FLOAT_X, -1);
        float y = sp.getFloat(KEY_FLOAT_Y, -1);
        if (x == -1 && y == -1) {
            x = getMeasuredWidth() - helperView.getMeasuredWidth();
            y = getMeasuredHeight() * 2 / 3;
        }
        helperView.layout((int) x, (int) y, (int) x + helperView.getMeasuredWidth(), (int) y + helperView.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private final class FloatCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == helperView;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (top > getHeight() - child.getMeasuredHeight()) {
                top = getHeight() - child.getMeasuredHeight();
            } else if (top < 0) {
                top = 0;
            }
            return top;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            if (left > getWidth() - child.getMeasuredWidth()) {
                left = getWidth() - child.getMeasuredWidth();
            } else if (left < 0) {
                left = 0;
            }
            return left;
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            savePosition();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (state == ViewDragHelper.STATE_SETTLING) {
                restorePosition();
            }
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (releasedChild == helperView) {
                float x = helperView.getX();
                float y = helperView.getY();
                if (x < (getMeasuredWidth() / 2f - releasedChild.getMeasuredWidth() / 2f)) {
                    if (x < releasedChild.getMeasuredWidth() / 3f) {
                        x = 0;
                    } else if (y < (releasedChild.getMeasuredHeight() * 3)) {
                        y = 0;
                    } else if (y > (getMeasuredHeight() - releasedChild.getMeasuredHeight() * 3)) {
                        y = getMeasuredHeight() - releasedChild.getMeasuredHeight();
                    } else {
                        x = 0;
                    }
                } else {
                    if (x > getMeasuredWidth() - releasedChild.getMeasuredWidth() / 3f - releasedChild.getMeasuredWidth()) {
                        x = getMeasuredWidth() - releasedChild.getMeasuredWidth();
                    } else if (y < (releasedChild.getMeasuredHeight() * 3)) {
                        y = 0;
                    } else if (y > (getMeasuredHeight() - releasedChild.getMeasuredHeight() * 3)) {
                        y = getMeasuredHeight() - releasedChild.getMeasuredHeight();
                    } else {
                        x = getMeasuredWidth() - releasedChild.getMeasuredWidth();
                    }
                }
                dragHelper.smoothSlideViewTo(releasedChild, (int) x, (int) y);
                invalidate();
            }
        }
    }
}