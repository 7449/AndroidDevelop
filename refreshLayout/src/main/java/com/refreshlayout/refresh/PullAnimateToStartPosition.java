package com.refreshlayout.refresh;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * by y on 2016/9/8
 */
public class PullAnimateToStartPosition extends Animation {

    private AnimateToStartPositionListener animateToStartPositionListener;

    public PullAnimateToStartPosition(AnimateToStartPositionListener animateToStartPositionListener) {
        this.animateToStartPositionListener = animateToStartPositionListener;
    }

    @Override
    public void applyTransformation(float interpolatedTime, Transformation t) {
        animateToStartPositionListener.onApplyTransformationToStart(interpolatedTime);
    }

    public interface AnimateToStartPositionListener {
        void onApplyTransformationToStart(float interpolatedTime);
    }
}
