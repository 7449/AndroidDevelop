package com.common.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public class ValueAnimatorUtils {

    public static final int DEFAULT_MIN_VALUE = 20;

    private ValueAnimator valueAnimator;

    private ValueAnimatorUtils() {
        valueAnimator = new ValueAnimator();
    }

    public static ValueAnimatorUtils newInstance() {
        return new ValueAnimatorUtils();
    }

    public ValueAnimatorUtils setIntValue(int... value) {
        if (valueAnimator != null) {
            valueAnimator.setIntValues(value);
        }
        return this;
    }

    public ValueAnimatorUtils setDuration(int duration) {
        if (valueAnimator != null) {
            valueAnimator.setDuration(duration);
        }
        return this;
    }

    public void start() {
        if (valueAnimator != null) {
            valueAnimator.start();
        }
    }

    public void cancel() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public ValueAnimatorUtils removeUpdateListener(ValueAnimator.AnimatorUpdateListener animatorListener) {
        if (valueAnimator != null && animatorListener != null) {
            valueAnimator.removeUpdateListener(animatorListener);
        }
        return this;
    }

    public ValueAnimatorUtils removeAllUpdateListeners() {
        if (valueAnimator != null) {
            valueAnimator.removeAllUpdateListeners();
        }
        return this;
    }

    public ValueAnimatorUtils addUpdateListener(ValueAnimator.AnimatorUpdateListener updateListener) {
        if (valueAnimator != null && updateListener != null) {
            valueAnimator.addUpdateListener(updateListener);
        }
        return this;
    }

    public ValueAnimatorUtils removeListener(Animator.AnimatorListener animatorListener) {
        if (valueAnimator != null && animatorListener != null) {
            valueAnimator.removeListener(animatorListener);
        }
        return this;
    }

    public ValueAnimatorUtils removeListener() {
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
        }
        return this;
    }

    public ValueAnimatorUtils addListener(Animator.AnimatorListener updateListener) {
        if (valueAnimator != null && updateListener != null) {
            valueAnimator.addListener(updateListener);
        }
        return this;
    }

    public static class ValueAnimatorHeightUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private View heightView;

        public ValueAnimatorHeightUpdateListener(@NonNull View heightView) {
            this.heightView = heightView;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            if (heightView == null) {
                return;
            }
            int value = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = heightView.getLayoutParams();
            layoutParams.height = value;
            heightView.setLayoutParams(layoutParams);
        }
    }

    public static class ValueAnimatorWidthUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private View widthView;

        public ValueAnimatorWidthUpdateListener(@NonNull View widthView) {
            this.widthView = widthView;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            if (widthView == null) {
                return;
            }
            int value = (int) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = widthView.getLayoutParams();
            layoutParams.width = value;
            widthView.setLayoutParams(layoutParams);
        }
    }
}