package com.rn.netdetail.floattool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.rn.netdetail.R;
import com.rn.netdetail.Utils;
import com.rn.netdetail.ui.NetDetailListActivity;

public class FloatingDraggedView extends FrameLayout {

    ViewDragHelper dragHelper;
    SharedPreferences sp = getContext().getSharedPreferences("FloatingDraggedView", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    Button floatingBtn;
    ObservableCall observableCall;

    public static final String KEY_FLOATING_X = "KEY_FLOATING_X";
    public static final String KEY_FLOATING_Y = "KEY_FLOATING_Y";

    public FloatingDraggedView(Context context) {
        super(context);
        init();
    }

    public FloatingDraggedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatingDraggedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        restorePosition();
    }

    void init() {
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == floatingBtn;
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
                    observableCall.getObservable().update();
                }
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                if (releasedChild == floatingBtn) {
                    float x = floatingBtn.getX();
                    float y = floatingBtn.getY();
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
        });
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        savePosition();
        observableCall.getObservable().deleteObserver(observableCall.getFloatingHelper());
    }

    void savePosition() {
        float x = floatingBtn.getX();
        float y = floatingBtn.getY();
        editor.putFloat(KEY_FLOATING_X, x);
        editor.putFloat(KEY_FLOATING_Y, y);
        editor.commit();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        floatingBtn = findViewById(R.id.floatingBtn);
        floatingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    public void showDialog() {
        final AlertDialog show = new AlertDialog.Builder(getContext())
                .setView(R.layout.layout_dialog)
                .show();
        show.findViewById(R.id.btn_net).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NetDetailListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                show.dismiss();
            }
        });
        show.findViewById(R.id.btn_screenshots).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = Utils.captureContent((Activity) getContext());
                String path = Utils.saveImage(bitmap);
                Utils.shareImage(getContext(), path);
                show.dismiss();
            }
        });
    }

    public void restorePosition() {
        float x = sp.getFloat(KEY_FLOATING_X, -1);
        float y = sp.getFloat(KEY_FLOATING_Y, -1);
        if (x == -1 && y == -1) {
            x = getMeasuredWidth() - floatingBtn.getMeasuredWidth();
            y = getMeasuredHeight() * 2 / 3;
        }
        floatingBtn.layout((int) x, (int) y,
                (int) x + floatingBtn.getMeasuredWidth(), (int) y + floatingBtn.getMeasuredHeight());
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

    public void setObservableCall(ObservableCall observerCall) {
        this.observableCall = observerCall;
    }
}