package sample.view.develop.android.refresh.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

public class PullToRefreshView extends ViewGroup
        implements PullAnimateToStartPosition.AnimateToStartPositionListener,
        PullAnimateToCorrectPosition.AnimateToCorrectPositionListener, Animation.AnimationListener {

    public static final int STATUS_IDLE = 0;  //默认
    public static final int STATUS_PULL_TO_REFRESH = 1; //下拉刷新
    public static final int STATUS_PULL_TO_LOAD = 2;  //松手刷新
    public static final int STATUS_REFRESHING = 3;  //正在刷新
    public static final int STATUS_REFRESH_SUCCESS = 4;  //刷新成功
    public static final int STATUS_REFRESH_ERROR = 5;  // 刷新失败

    private static final int DRAG_MAX_DISTANCE = 62;
    private static final float DRAG_RATE = .5f;
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2f;
    private static final int MAX_OFFSET_ANIMATION_DURATION = 400;

    private View mTarget;
    private ImageView mRefreshView;
    private Interpolator mDecelerateInterpolator;
    private int mTouchSlop;
    private int mTotalDragDistance;
    private BaseRefreshView mBaseRefreshView;
    private float mCurrentDragPercent;
    private int mCurrentOffsetTop;
    private boolean mIsBeingDragged;
    private float mInitialMotionY;
    private int mFrom;
    private float mFromDragPercent;

    private PullAnimateToStartPosition mPullAnimateToStartPosition;
    private PullAnimateToCorrectPosition mPullAnimateToCorrectPosition;

    private int mCurrentTextColor = -1;
    private OnRefreshListener mListener;
    private int mTargetPaddingTop;
    private int mTargetPaddingBottom;
    private int mTargetPaddingRight;
    private int mTargetPaddingLeft;

    private int mStatus = STATUS_PULL_TO_REFRESH;

    public PullToRefreshView(Context context) {
        this(context, null);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mTotalDragDistance = dp2Px(DRAG_MAX_DISTANCE);
        mRefreshView = new ImageView(getContext());
        mBaseRefreshView = new NormalRefreshView(getContext(), this);
        mRefreshView.setImageDrawable(mBaseRefreshView);
        addView(mRefreshView);
        setWillNotDraw(false);
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
        mPullAnimateToStartPosition = new PullAnimateToStartPosition(this);
        mPullAnimateToCorrectPosition = new PullAnimateToCorrectPosition(this);
    }

    public int dp2Px(float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    public int getTotalDragDistance() {
        return mTotalDragDistance;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ensureTarget();
        if (mTarget == null) {
            return;
        }

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingRight() - getPaddingLeft(), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY);
        mTarget.measure(widthMeasureSpec, heightMeasureSpec);
        mRefreshView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    private void ensureTarget() {
        if (mTarget != null)
            return;
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child != mRefreshView) {
                    mTarget = child;
                    mTargetPaddingBottom = mTarget.getPaddingBottom();
                    mTargetPaddingLeft = mTarget.getPaddingLeft();
                    mTargetPaddingRight = mTarget.getPaddingRight();
                    mTargetPaddingTop = mTarget.getPaddingTop();
                    break;
                }
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!isEnabled() || canChildScrollUp() || mStatus == STATUS_REFRESHING) {
            return false;
        }

        final int action = MotionEventCompat.getActionMasked(ev);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setTargetOffsetTop(0);
                mIsBeingDragged = false;
                final float initialMotionY = ev.getY();
                if (initialMotionY == -1) {
                    return false;
                }
                mInitialMotionY = initialMotionY;
                break;
            case MotionEvent.ACTION_MOVE:
                final float y = ev.getY();
                if (y == -1) {
                    return false;
                }
                final float yDiff = y - mInitialMotionY;
                if (yDiff > mTouchSlop && !mIsBeingDragged) {
                    mIsBeingDragged = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                break;
        }

        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {

        if (!mIsBeingDragged) {
            return super.onTouchEvent(ev);
        }

        final int action = MotionEventCompat.getActionMasked(ev);

        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                final float yDiff = ev.getY() - mInitialMotionY;
                final float scrollTop = yDiff * DRAG_RATE;
                mCurrentDragPercent = scrollTop / mTotalDragDistance;
                if (mCurrentDragPercent < 0) {
                    return false;
                }
                float boundedDragPercent = Math.min(1f, Math.abs(mCurrentDragPercent));
                float extraOS = Math.abs(scrollTop) - mTotalDragDistance;
                float slingshotDist = mTotalDragDistance;
                float tensionSlingshotPercent = Math.max(0,
                        Math.min(extraOS, slingshotDist * 2) / slingshotDist);
                float tensionPercent = (float) ((tensionSlingshotPercent / 4) - Math.pow(
                        (tensionSlingshotPercent / 4), 2)) * 2f;
                float extraMove = (slingshotDist) * tensionPercent / 2;
                int targetY = (int) ((slingshotDist * boundedDragPercent) + extraMove);

                mBaseRefreshView.setPercent(mCurrentDragPercent, true);
                setTargetOffsetTop(targetY - mCurrentOffsetTop);

                if (mCurrentDragPercent < 1)
                    changeStatus(STATUS_PULL_TO_REFRESH);
                else
                    changeStatus(STATUS_PULL_TO_LOAD);

                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                final float overScrollTop = (ev.getY() - mInitialMotionY) * DRAG_RATE;
                mIsBeingDragged = false;
                if (overScrollTop > mTotalDragDistance) {
                    changeStatus(STATUS_REFRESHING);

                    if (mListener != null)
                        mListener.onRefresh();

                } else {
                    changeStatus(STATUS_IDLE);
                }
                return false;
            }
        }

        return true;
    }

    private void animateOffsetToStartPosition() {
        mFrom = mCurrentOffsetTop;
        mFromDragPercent = mCurrentDragPercent;
        long animationDuration = Math.abs((long) (MAX_OFFSET_ANIMATION_DURATION * mFromDragPercent));
        mPullAnimateToStartPosition.reset();
        mPullAnimateToStartPosition.setDuration(animationDuration);
        mPullAnimateToStartPosition.setInterpolator(mDecelerateInterpolator);
        mPullAnimateToStartPosition.setAnimationListener(this);
        mRefreshView.clearAnimation();
        mRefreshView.startAnimation(mPullAnimateToStartPosition);
    }

    private void animateOffsetToCorrectPosition() {
        ensureTarget();
        mFrom = mCurrentOffsetTop;
        mFromDragPercent = mCurrentDragPercent;
        mPullAnimateToCorrectPosition.reset();
        mPullAnimateToCorrectPosition.setDuration(MAX_OFFSET_ANIMATION_DURATION);
        mPullAnimateToCorrectPosition.setInterpolator(mDecelerateInterpolator);
        mRefreshView.clearAnimation();
        mRefreshView.startAnimation(mPullAnimateToCorrectPosition);
        mBaseRefreshView.start();

        mCurrentOffsetTop = mTarget.getTop();
        mTarget.setPadding(mTargetPaddingLeft, mTargetPaddingTop, mTargetPaddingRight, mTotalDragDistance);
    }

    private void moveToStart(float interpolatedTime) {
        int targetTop = mFrom - (int) (mFrom * interpolatedTime);
        float targetPercent = mFromDragPercent * (1.0f - interpolatedTime);
        int offset = targetTop - mTarget.getTop();

        mCurrentDragPercent = targetPercent;
        mBaseRefreshView.setPercent(mCurrentDragPercent, true);
        mTarget.setPadding(mTargetPaddingLeft, mTargetPaddingTop, mTargetPaddingRight, mTargetPaddingBottom + targetTop);
        setTargetOffsetTop(offset);
    }

    public void changeStatus(final int status) {
        if (mStatus == status)
            return;

        mStatus = status;
        switch (mStatus) {
            case STATUS_REFRESHING:
                mBaseRefreshView.setPercent(1f, true);
                animateOffsetToCorrectPosition();
                break;
            case STATUS_REFRESH_ERROR:
            case STATUS_REFRESH_SUCCESS:
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        animateOffsetToStartPosition();
                    }
                }, 300);
                break;
            case STATUS_IDLE:
                animateOffsetToStartPosition();
                break;
        }
    }


    public void setTextColor(int color) {
        if (mCurrentTextColor == color) {
            return;
        }
        mCurrentTextColor = color;
        mBaseRefreshView.setTextColor(mCurrentTextColor);
    }

    public int getStatus() {
        return mStatus;
    }

    private void setTargetOffsetTop(int offset) {
        mTarget.offsetTopAndBottom(offset);
        mBaseRefreshView.offsetTopAndBottom(offset);
        mCurrentOffsetTop = mTarget.getTop();
    }

    private boolean canChildScrollUp() {
        return ViewCompat.canScrollVertically(mTarget, -1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ensureTarget();
        if (mTarget == null)
            return;

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        mTarget.layout(left, top + mCurrentOffsetTop, left + width - right, top + height - bottom + mCurrentOffsetTop);
        mRefreshView.layout(left, top, left + width - right, top + height - bottom);
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    @Override
    public void onApplyTransformationToCorrect(float interpolatedTime) {
        int targetTop;
        int endTarget = mTotalDragDistance;
        targetTop = (mFrom + (int) ((endTarget - mFrom) * interpolatedTime));
        int offset = targetTop - mTarget.getTop();
        mCurrentDragPercent = mFromDragPercent - (mFromDragPercent - 1.0f) * interpolatedTime;
        mBaseRefreshView.setPercent(mCurrentDragPercent, false);
        setTargetOffsetTop(offset);
    }

    @Override
    public void onApplyTransformationToStart(float interpolatedTime) {
        moveToStart(interpolatedTime);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mBaseRefreshView.stop();
        mCurrentOffsetTop = mTarget.getTop();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public boolean isRefresh() {
        return mStatus == STATUS_REFRESHING;
    }
}

