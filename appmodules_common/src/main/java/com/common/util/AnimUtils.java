package com.common.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class AnimUtils {

    private int mWidth;
    private View hideView;
    private ImageView showView;
    private int openImageId;
    private int closeImageId;

    private AnimUtils(View hideView, ImageView showView, int width, int openImageId, int closeImageId) {
        this.hideView = hideView;
        this.showView = showView;
        this.openImageId = openImageId;
        this.closeImageId = closeImageId;
        mWidth = width;
    }

    public static AnimUtils newInstance(View hideView, ImageView showView, int width, int openImageId, int closeImageId) {
        return new AnimUtils(hideView, showView, width, openImageId, closeImageId);
    }

    public void toggle() {
        if (View.VISIBLE == hideView.getVisibility()) {
            closeAnimate(hideView);
        } else {
            openAnim(hideView);
        }
    }

    private void openAnim(View v) {
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v, 0, mWidth);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                showView.setImageResource(closeImageId);
            }
        });
        animator.start();
    }

    private void closeAnimate(final View view) {
        int origWidth = view.getWidth();
        ValueAnimator animator = createDropAnimator(view, origWidth, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                showView.setImageResource(openImageId);
            }
        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(500);
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
            layoutParams.width = value;
            v.setLayoutParams(layoutParams);
        });
        return animator;
    }
}