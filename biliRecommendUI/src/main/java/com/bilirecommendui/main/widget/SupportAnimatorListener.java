package com.bilirecommendui.main.widget;

import io.codetail.animation.SupportAnimator;

/**
 * by y on 2016/9/22
 */

public class SupportAnimatorListener implements SupportAnimator.AnimatorListener {

    public static final int TYPE_START = 0;
    public static final int TYPE_END = 1;

    private int type = -1;
    private AnimatorInterface animatorInterface = null;

    public SupportAnimatorListener(int type, AnimatorInterface animatorInterface) {
        this.type = type;
        this.animatorInterface = animatorInterface;
    }


    @Override
    public void onAnimationStart() {
        if (null == animatorInterface) {
            return;
        }
        switch (type) {
            case TYPE_START:
                animatorInterface.onStartAnimationStart();
                break;
            default:
                animatorInterface.onEndAnimationStart();
                break;
        }
    }

    @Override
    public void onAnimationEnd() {
        if (null == animatorInterface) {
            return;
        }
        switch (type) {
            case TYPE_START:
                animatorInterface.onStartAnimationEnd();
                break;
            default:
                animatorInterface.onEndAnimationEnd();
                break;
        }
    }

    @Override
    public void onAnimationCancel() {

    }

    @Override
    public void onAnimationRepeat() {

    }

    public interface AnimatorInterface {
        void onStartAnimationStart();

        void onStartAnimationEnd();

        void onEndAnimationStart();

        void onEndAnimationEnd();
    }
}
