package sample.view.develop.android.refresh.refresh;


import static android.graphics.Bitmap.createScaledBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import androidx.core.content.ContextCompat;

import sample.view.develop.android.refresh.R;

public class NormalRefreshView extends BaseRefreshView implements Animatable {
    private static final int ANIMATION_DURATION = 1000;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private PullToRefreshView mParent;
    private Matrix mMatrix;
    private Animation mAnimation;
    private Paint textPaint;
    private int mTop;
    private int mScreenWidth;
    private int mIndicatorHalfSize;
    private float mIndicatorLeftOffset;
    private float mIndicatorTopOffset;
    private float mRotate = 0.0f;
    private Bitmap mIndicatorBitmap;
    private float mTextLeftOffset;
    private float mTextTopOffset;

    public NormalRefreshView(Context context, final PullToRefreshView parent) {
        super(context, parent);
        mParent = parent;
        mMatrix = new Matrix();
        mIndicatorHalfSize = context.getResources().getDimensionPixelSize(R.dimen.loading_indicator_size) / 3;
        setupAnimations();
        parent.post(new Runnable() {
            @Override
            public void run() {
                initiateDimens(parent.getWidth());
            }
        });
        textPaint = new Paint();
        textPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.loading_text_size));
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.gray));
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mIndicatorBitmap = getBitmap(R.drawable.ic_refresh_loading);
        mMatrix = new Matrix();
    }

    private void initiateDimens(int viewWidth) {
        if (viewWidth <= 0 || viewWidth == mScreenWidth)
            return;

        mScreenWidth = viewWidth;
        mIndicatorLeftOffset = 0.35f * (float) mScreenWidth;
        mIndicatorTopOffset = mParent.getTotalDragDistance() * 0.2f;
        mTop = -mParent.getTotalDragDistance();
        mTextLeftOffset = mIndicatorHalfSize * 3f;
        mTextTopOffset = mParent.getTotalDragDistance() * 0.2f;
    }

    @Override
    public void setPercent(float percent, boolean invalidate) {
        if (invalidate)
            setRotate(percent);
    }

    @Override
    public void offsetTopAndBottom(int offset) {
        mTop += offset;
        invalidateSelf();
    }

    @Override
    public void setTextColor(int color) {
        textPaint.setColor(mParent.getResources().getColor(color));
    }

    @Override
    public void draw(Canvas canvas) {
        if (mScreenWidth <= 0) return;
        final int saveCount = canvas.save();
        drawIndicator(canvas);
        canvas.restoreToCount(saveCount);
    }


    private void drawIndicator(Canvas canvas) {
        if (mParent.getStatus() == PullToRefreshView.STATUS_REFRESH_SUCCESS) {
            mIndicatorBitmap = getBitmap(R.drawable.ic_refresh_success);
            mMatrix.reset();
            mMatrix.postRotate(0, mIndicatorHalfSize, mIndicatorHalfSize);
        } else {
            mIndicatorBitmap = getBitmap(R.drawable.ic_refresh_loading);
            boolean isRefresh = mParent.getStatus() == PullToRefreshView.STATUS_REFRESHING || mParent.getStatus() == PullToRefreshView.STATUS_REFRESH_ERROR;
            float degree = (isRefresh ? 180 : -180) * mRotate;
            mMatrix.reset();
            mMatrix.postRotate(degree, mIndicatorHalfSize, mIndicatorHalfSize);
        }
        canvas.translate(mIndicatorLeftOffset, mIndicatorTopOffset + mTop);
        canvas.drawBitmap(mIndicatorBitmap, mMatrix, null);
        canvas.drawText(getDescription(mParent.getStatus()), mTextLeftOffset, mTextTopOffset, textPaint);
    }

    private void setRotate(float rotate) {
        mRotate = rotate;
        invalidateSelf();
    }

    private Bitmap getBitmap(int id) {
        return createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), id), mIndicatorHalfSize * 2, mIndicatorHalfSize * 2, true);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void start() {
        mAnimation.reset();
        mParent.startAnimation(mAnimation);
    }

    @Override
    public void stop() {
        mParent.clearAnimation();
    }

    private void setupAnimations() {
        mAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                setRotate(interpolatedTime);
            }
        };
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setInterpolator(LINEAR_INTERPOLATOR);
        mAnimation.setDuration(ANIMATION_DURATION);
    }
}
