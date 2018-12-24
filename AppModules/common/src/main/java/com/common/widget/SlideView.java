package com.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.common.R;


/**
 * by y on 2016/6/7.
 */
public class SlideView extends View {

    private static final String TAG = "SlideView";

    private String[] mark;

    private OnSlideTouchListener listener;

    private Paint mPaint;


    private TextView promptBox;


    private boolean isTouch = false;


    public SlideView(Context context) {
        super(context);
        init();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPromptBox(TextView promptBox) {
        this.promptBox = promptBox;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(getResources().getDimension(R.dimen.dp_14));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mark = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    }

    public void setOnTouchListener(OnSlideTouchListener onTouchListener) {
        this.listener = onTouchListener;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int textHeight = getHeight() / mark.length;

        for (int i = 0; i < mark.length; i++) {
            canvas.drawText(mark[i], getWidth() / 2, textHeight + (i * textHeight), mPaint);
        }

        if (isTouch) {
            canvas.drawColor(getContext().getResources().getColor(R.color.colorBg));
        }

        Log.i(TAG, "onDraw  -->");
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float y = event.getY();
        int letter = (int) (y / getHeight() * mark.length);
        if (letter < 0 || letter > mark.length - 1) {
            isTouch = false;
            invalidate();
            if (promptBox != null) {
                promptBox.setVisibility(GONE);
            }
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                touchIndex(letter);
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                if (promptBox != null) {
                    promptBox.setVisibility(GONE);
                }
                break;
        }
        invalidate();
        return true;
    }

    private void touchIndex(int letter) {
        if (letter >= 0 && letter < mark.length) {
            listener.onSlideTouch(mark[letter]);
            if (promptBox != null) {
                promptBox.setVisibility(VISIBLE);
                promptBox.setText(mark[letter]);
            }
        }

    }


    public String[] getMark() {
        return mark;
    }

    public void setMark(String[] mark) {
        this.mark = mark;
    }

    public interface OnSlideTouchListener {
        void onSlideTouch(String letter);
    }
}
