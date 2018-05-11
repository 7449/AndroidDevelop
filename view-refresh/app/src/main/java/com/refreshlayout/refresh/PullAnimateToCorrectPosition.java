package com.refreshlayout.refresh;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * by y on 2016/9/8
 */
public class PullAnimateToCorrectPosition extends Animation {

    private AnimateToCorrectPositionListener animateToCorrectPositionListener;

    public PullAnimateToCorrectPosition(AnimateToCorrectPositionListener animateToCorrectPositionListener) {
        this.animateToCorrectPositionListener = animateToCorrectPositionListener;
    }

    @Override
    public void applyTransformation(float interpolatedTime, Transformation t) {
        animateToCorrectPositionListener.onApplyTransformationToCorrect(interpolatedTime);
    }

    public interface AnimateToCorrectPositionListener {
        void onApplyTransformationToCorrect(float interpolatedTime);
    }
}
