package com.androiddevelop.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * by y on 2016/10/28
 */

public class TimeButton extends Button {

    public TimeButton(Context context) {
        super(context);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void startButton() {
        setEnabled(false);
        new TimeCountUtil(60 * 1000, 1000).start();
    }

    public class TimeCountUtil extends CountDownTimer {

        public TimeCountUtil(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @SuppressLint("NewApi")
        public void onTick(long millisUntilFinished) {
            setText("剩余 " + millisUntilFinished / 1000 + " 秒");
        }

        @SuppressLint("NewApi")
        public void onFinish() {
            setText("再次点击");
            setEnabled(true);
        }

    }
}
