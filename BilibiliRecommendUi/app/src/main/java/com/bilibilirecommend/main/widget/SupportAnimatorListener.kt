package com.bilibilirecommend.main.widget

import io.codetail.animation.SupportAnimator

class SupportAnimatorListener(var type: Int, var animatorInterface: AnimatorInterface) : SupportAnimator.AnimatorListener {

    companion object {
        const val TYPE_START = 0
        const val TYPE_END = 1
    }


    override fun onAnimationStart() {
        when (type) {
            TYPE_START -> animatorInterface.onStartAnimationStart()
            else -> animatorInterface.onEndAnimationStart()
        }
    }

    override fun onAnimationEnd() {
        when (type) {
            TYPE_START -> animatorInterface.onStartAnimationEnd()
            else -> animatorInterface.onEndAnimationEnd()
        }
    }

    override fun onAnimationCancel() {

    }

    override fun onAnimationRepeat() {

    }

    interface AnimatorInterface {
        fun onStartAnimationStart()

        fun onStartAnimationEnd()

        fun onEndAnimationStart()

        fun onEndAnimationEnd()
    }

}
