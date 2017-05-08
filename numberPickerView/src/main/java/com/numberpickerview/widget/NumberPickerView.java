package com.numberpickerview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.ScrollerCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

import com.numberpickerview.R;
import com.numberpickerview.widget.handler.PickerHandlerThread;
import com.numberpickerview.widget.handler.PickerInMainHandler;
import com.numberpickerview.widget.listener.OnScrollListener;
import com.numberpickerview.widget.listener.OnValueChangeListener;
import com.numberpickerview.widget.listener.OnValueChangeListenerInScrolling;
import com.numberpickerview.widget.listener.OnValueChangeListenerRelativeToRaw;


public class NumberPickerView extends View implements PickerInMainHandler.PickerInMainListener {

    private int mSpecModeW = MeasureSpec.UNSPECIFIED;
    private int mSpecModeH = MeasureSpec.UNSPECIFIED;
    private float downYGlobal = 0;
    private float downY = 0;
    private float currY = 0;
    private int mInScrollingPickedOldValue;
    private int mInScrollingPickedNewValue;
    //first shown item's content index, corresponding to the Index of mDisplayedValued
    private int mCurrDrawFirstItemIndex = 0;
    //the first shown item's Y
    private int mCurrDrawFirstItemY = 0;

    //global Y corresponding to scroller
    private int mCurrDrawGlobalY = 0;
    private int mViewWidth;
    private int mViewHeight;
    private int mItemHeight;

    private float dividerY0;
    private float dividerY1;
    private float mViewCenterX;
    private boolean mFlagMayPress = false;
    private int mNotWrapLimitYTop;

    private int mNotWrapLimitYBottom;
    // NumberPickerView 的滚动状态
    private int mScrollState = OnScrollListener.SCROLL_STATE_IDLE;
    private String[] mDisplayedValues;  // item填充的数据
    private int mTextColorNormal; //未选中item的字体颜色
    private int mTextColorSelected; //选中item的字体颜色
    private int mTextColorHint; // 提示文本的字体颜色
    private int mTextSizeNormal; // 未选中item的字体大小
    private int mTextSizeSelected; //选中item的字体大小
    private int mTextSizeHint; //提示文本的字体大小
    private int mWidthOfHintText = 0; //
    private int mWidthOfAlterHint = 0; //
    private int mMarginStartOfHint = 0; //
    private int mMarginEndOfHint = 0; //
    private int mItemPaddingVertical; //
    private int mItemPaddingHorizontal; //
    private int mDividerColor; //分割线颜色
    private float mDividerHeight; //分割线高度
    private int mDividerMarginL; //分割线的 marginLeft
    private int mDividerMarginR; //分割线的 marginRight
    private int mShowCount; //显示几个item
    private int mDividerIndex0 = 0;
    private int mDividerIndex1 = 0;
    private int mMinShowIndex;
    private int mMaxShowIndex;
    private int mMinValue = 0;
    private int mMaxValue = 0;
    private int mMaxWidthOfDisplayedValues = 0;
    private int mMaxHeightOfDisplayedValues = 0;
    private int mMaxWidthOfAlterArrayWithMeasureHint = 0;
    private int mMaxWidthOfAlterArrayWithoutMeasureHint = 0;
    private int mPrevPickedIndex = 0;
    private int mMiniVelocityFling = 150;
    private int mScaledTouchSlop = 8;
    private String mHintText; // 提示文本
    private String mTextEllipsize; // text的flag
    private String mEmptyItemHint;
    private String mAlterHint;
    private float mFriction = 1f;//滑动时的阻尼
    private float mTextSizeNormalCenterYOffset = 0f;
    private float mTextSizeSelectedCenterYOffset = 0f;
    private float mTextSizeHintCenterYOffset = 0f;
    private boolean mShowDivider; //是否显示两根分割线
    //true to wrap the displayed values
    private boolean mWrapSelectorWheel;
    //true to set to the current position, false set position to 0
    private boolean mCurrentItemIndexEffect = NumberPickerDefault.DEFAULT_CURRENT_ITEM_INDEX_EFFECT;
    //true if NumberPickerView has initialized
    private boolean mHasInit = false;
    // if displayed values' number is less than show count, then this value will be false.
    private boolean mWrapSelectorWheelCheck = true;
    // if you want you set to linear mode from wrap mode when scrolling, then this value will be true.
    private boolean mPendingWrapToLinear = false;

    // if this view is used in same dialog or PopupWindow more than once, and there are several
    // NumberPickerViews linked, such as Gregorian Calendar with MonthPicker and DayPicker linked,
    // set mRespondChangeWhenDetach true to respond onValueChanged callbacks if this view is scrolling
    // when detach from window, but this solution is unlovely and may cause NullPointerException
    // (even i haven't found this NullPointerException),
    // so I highly recommend that every time setting up a reusable dialog with a NumberPickerView in it,
    // please initialize NumberPickerView's data, and in this way, you can set mRespondChangeWhenDetach false.
    private boolean mRespondChangeOnDetach;

    // this is to set which thread to respond onChange... listeners including
    // OnValueChangeListener, OnValueChangeListenerRelativeToRaw and OnScrollListener when view is
    // scrolling or starts to scroll or stops scrolling.
    private boolean mRespondChangeInMainThread;

    private ScrollerCompat mScroller;
    private VelocityTracker mVelocityTracker;

    private Paint mPaintDivider = new Paint();
    private TextPaint mPaintText = new TextPaint();
    private Paint mPaintHint = new Paint();

    private CharSequence[] mAlterTextArrayWithMeasureHint;
    private CharSequence[] mAlterTextArrayWithoutMeasureHint;

    private PickerHandlerThread mHandlerThread;
    private Handler mHandlerInNewThread;
    private PickerInMainHandler mHandlerInMainThread;

    private OnValueChangeListenerRelativeToRaw mOnValueChangeListenerRaw;
    private OnValueChangeListener mOnValueChangeListener;
    private OnScrollListener mOnScrollListener;
    private OnValueChangeListenerInScrolling mOnValueChangeListenerInScrolling;


    public NumberPickerView(Context context) {
        super(context);
        init(context);
    }

    public NumberPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init(context);
    }

    public NumberPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init(context);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.NumberPickerView);
        //显示几个item
        mShowCount = array.getInt(R.styleable.NumberPickerView_npv_ShowCount, NumberPickerDefault.DEFAULT_SHOW_COUNT);
        //分割线颜色
        mDividerColor = array.getColor(R.styleable.NumberPickerView_npv_DividerColor, NumberPickerDefault.DEFAULT_DIVIDER_COLOR);
        //分割线高度
        mDividerHeight = array.getDimensionPixelSize(R.styleable.NumberPickerView_npv_DividerHeight, NumberPickerDefault.DEFAULT_DIVIDER_HEIGHT);
        //分割线的 marginLeft
        mDividerMarginL = array.getDimensionPixelSize(R.styleable.NumberPickerView_npv_DividerMarginLeft, NumberPickerDefault.DEFAULT_DIVIDER_MARGIN_HORIZONTAL);
        //分割线的 marginRight
        mDividerMarginR = array.getDimensionPixelSize(R.styleable.NumberPickerView_npv_DividerMarginRight, NumberPickerDefault.DEFAULT_DIVIDER_MARGIN_HORIZONTAL);
        //是否显示两条分割线
        mShowDivider = array.getBoolean(R.styleable.NumberPickerView_npv_ShowDivider, NumberPickerDefault.DEFAULT_SHOW_DIVIDER);
        //item的数据
        mDisplayedValues = NumberPickerUtils.convertCharSequenceArrayToStringArray(array.getTextArray(R.styleable.NumberPickerView_npv_TextArray));
        //未选中item的字体颜色
        mTextColorNormal = array.getColor(R.styleable.NumberPickerView_npv_TextColorNormal, NumberPickerDefault.DEFAULT_TEXT_COLOR_NORMAL);
        //选中item的字体颜色
        mTextColorSelected = array.getColor(R.styleable.NumberPickerView_npv_TextColorSelected, NumberPickerDefault.DEFAULT_TEXT_COLOR_SELECTED);
        //提示文本的字体颜色
        mTextColorHint = array.getColor(R.styleable.NumberPickerView_npv_TextColorHint, NumberPickerDefault.DEFAULT_TEXT_COLOR_SELECTED);
        //未选中item的字体大小
        mTextSizeNormal = array.getDimensionPixelSize(R.styleable.NumberPickerView_npv_TextSizeNormal, NumberPickerUtils.sp2px(context, NumberPickerDefault.DEFAULT_TEXT_SIZE_NORMAL_SP));
        //选中item的字体大小
        mTextSizeSelected = array.getDimensionPixelSize(R.styleable.NumberPickerView_npv_TextSizeSelected, NumberPickerUtils.sp2px(context, NumberPickerDefault.DEFAULT_TEXT_SIZE_SELECTED_SP));
        //提示文本的字体大小
        mTextSizeHint = array.getDimensionPixelSize(R.styleable.NumberPickerView_npv_TextSizeHint, NumberPickerUtils.sp2px(context, NumberPickerDefault.DEFAULT_TEXT_SIZE_HINT_SP));
        //最小值  setMinValue
        mMinShowIndex = array.getInteger(R.styleable.NumberPickerView_npv_MinValue, 0);
        //最大值 setMaxValue
        mMaxShowIndex = array.getInteger(R.styleable.NumberPickerView_npv_MaxValue, 0);
        //是否 wrap setWrapSelectorWheel
        mWrapSelectorWheel = array.getBoolean(R.styleable.NumberPickerView_npv_WrapSelectorWheel, NumberPickerDefault.DEFAULT_WRAP_SELECTOR_WHEEL);
        //提示文本
        mHintText = array.getString(R.styleable.NumberPickerView_npv_HintText);
        //文字的最大宽度
        mAlterHint = array.getString(R.styleable.NumberPickerView_npv_AlternativeHint);
        //空行的显示文字，默认不显示任何文字。只在 WrapSelectorWheel==false 时起作用
        mEmptyItemHint = array.getString(R.styleable.NumberPickerView_npv_EmptyItemHint);
        //选中item的内容与提示文本的距离
        mMarginStartOfHint = array.getDimensionPixelSize(R.styleable.NumberPickerView_npv_MarginStartOfHint, NumberPickerUtils.dp2px(context, NumberPickerDefault.DEFAULT_MARGIN_START_OF_HINT_DP));
        //提示文本距离 NumberPickerView 右侧之间的距离, 适用于
        mMarginEndOfHint = array.getDimensionPixelSize(R.styleable.NumberPickerView_npv_MarginEndOfHint, NumberPickerUtils.dp2px(context, NumberPickerDefault.DEFAULT_MARGIN_END_OF_HINT_DP));
        //默认在 vertical 模式下的padding,左右都是 2dp ，仅适用于 wrap_content
        mItemPaddingVertical = array.getDimensionPixelSize(R.styleable.NumberPickerView_npv_ItemPaddingVertical, NumberPickerUtils.dp2px(context, NumberPickerDefault.DEFAULT_ITEM_PADDING_DP_V));
        //默认在 horizontal 模式下的padding,左右都是 5dp ，仅适用于 wrap_content
        mItemPaddingHorizontal = array.getDimensionPixelSize(R.styleable.NumberPickerView_npv_ItemPaddingHorizontal, NumberPickerUtils.dp2px(context, NumberPickerDefault.DEFAULT_ITEM_PADDING_DP_H));
        //用于在wrap_content模式下，改变内容array并且又不想让控件"跳动"，那么就可以设置所有改变的内容的最大宽度
        mAlterTextArrayWithMeasureHint = array.getTextArray(R.styleable.NumberPickerView_npv_AlternativeTextArrayWithMeasureHint);
        //可能达到的最大宽度，不包括说明文字在内，最大宽度只可能比此String的宽度+说明文字+说明文字marginstart +说明文字marginend 更大
        mAlterTextArrayWithoutMeasureHint = array.getTextArray(R.styleable.NumberPickerView_npv_AlternativeTextArrayWithoutMeasureHint);
        //在detach时如果NumberPickerView正好滑动，设置
        //是否响应onValueChange回调，用在一个Dialog/PopupWindow被显示多次，
        //且多次显示时记录上次滑动状态的情况。建议Dialog/PopupWindow在显示时每次都指定初始值，且将此属性置为false
        mRespondChangeOnDetach = array.getBoolean(R.styleable.NumberPickerView_npv_RespondChangeOnDetached, NumberPickerDefault.DEFAULT_RESPOND_CHANGE_ON_DETACH);
        //指定`onValueChanged`响应事件在什么线程中执行。 默认为`true`，即在主线程中执行。如果设置为`false`则在子线程中执行。
        mRespondChangeInMainThread = array.getBoolean(R.styleable.NumberPickerView_npv_RespondChangeInMainThread, NumberPickerDefault.DEFAULT_RESPOND_CHANGE_IN_MAIN_THREAD);
        mTextEllipsize = array.getString(R.styleable.NumberPickerView_npv_TextEllipsize);
        array.recycle();
    }

    private void init(Context context) {
        mScroller = ScrollerCompat.create(context);
        mMiniVelocityFling = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        if (mTextSizeNormal == 0)
            mTextSizeNormal = NumberPickerUtils.sp2px(context, NumberPickerDefault.DEFAULT_TEXT_SIZE_NORMAL_SP);
        if (mTextSizeSelected == 0)
            mTextSizeSelected = NumberPickerUtils.sp2px(context, NumberPickerDefault.DEFAULT_TEXT_SIZE_SELECTED_SP);
        if (mTextSizeHint == 0)
            mTextSizeHint = NumberPickerUtils.sp2px(context, NumberPickerDefault.DEFAULT_TEXT_SIZE_HINT_SP);
        if (mMarginStartOfHint == 0)
            mMarginStartOfHint = NumberPickerUtils.dp2px(context, NumberPickerDefault.DEFAULT_MARGIN_START_OF_HINT_DP);
        if (mMarginEndOfHint == 0)
            mMarginEndOfHint = NumberPickerUtils.dp2px(context, NumberPickerDefault.DEFAULT_MARGIN_END_OF_HINT_DP);

        mPaintDivider.setColor(mDividerColor);
        mPaintDivider.setAntiAlias(true);
        mPaintDivider.setStyle(Paint.Style.STROKE);
        mPaintDivider.setStrokeWidth(mDividerHeight);

        mPaintText.setColor(mTextColorNormal);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextAlign(Align.CENTER);

        mPaintHint.setColor(mTextColorHint);
        mPaintHint.setAntiAlias(true);
        mPaintHint.setTextAlign(Align.CENTER);
        mPaintHint.setTextSize(mTextSizeHint);

        if (mShowCount % 2 == 0) {
            mShowCount++;
        }
        if (mMinShowIndex == -1 || mMaxShowIndex == -1) {
            updateValueForInit();
        }
        initHandler();
    }

    private void initHandler() {
        mHandlerThread = new PickerHandlerThread(PickerHandlerThread.PICKER_HANDLER_NAME);
        mHandlerThread.start();

        mHandlerInNewThread = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case NumberPickerDefault.HANDLER_WHAT_REFRESH:
                        if (!mScroller.isFinished()) {
                            if (mScrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                                onScrollStateChange(OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
                            }
                            mHandlerInNewThread.sendMessageDelayed(NumberPickerUtils.getMsg(NumberPickerDefault.HANDLER_WHAT_REFRESH, 0, 0, msg.obj), NumberPickerDefault.HANDLER_INTERVAL_REFRESH);
                        } else {
                            int duration = 0;
                            int willPickIndex;
                            //if scroller finished(not scrolling), then adjust the position
                            if (mCurrDrawFirstItemY != 0) {//need to adjust
                                if (mScrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                                    onScrollStateChange(OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
                                }
                                if (mCurrDrawFirstItemY < (-mItemHeight / 2)) {
                                    //adjust to scroll upward
                                    duration = (int) ((float) NumberPickerDefault.DEFAULT_INTERVAL_REVISE_DURATION * (mItemHeight + mCurrDrawFirstItemY) / mItemHeight);
                                    mScroller.startScroll(0, mCurrDrawGlobalY, 0, mItemHeight + mCurrDrawFirstItemY, duration * 3);
                                    willPickIndex = getWillPickIndexByGlobalY(mCurrDrawGlobalY + mItemHeight + mCurrDrawFirstItemY);
                                } else {
                                    //adjust to scroll downward
                                    duration = (int) ((float) NumberPickerDefault.DEFAULT_INTERVAL_REVISE_DURATION * (-mCurrDrawFirstItemY) / mItemHeight);
                                    mScroller.startScroll(0, mCurrDrawGlobalY, 0, mCurrDrawFirstItemY, duration * 3);
                                    willPickIndex = getWillPickIndexByGlobalY(mCurrDrawGlobalY + mCurrDrawFirstItemY);
                                }
                                postInvalidate();
                            } else {
                                onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE);
                                //get the index which will be selected
                                willPickIndex = getWillPickIndexByGlobalY(mCurrDrawGlobalY);
                            }
                            Message changeMsg = NumberPickerUtils.getMsg(NumberPickerDefault.HANDLER_WHAT_LISTENER_VALUE_CHANGED, mPrevPickedIndex, willPickIndex, msg.obj);
                            if (mRespondChangeInMainThread) {
                                mHandlerInMainThread.sendMessageDelayed(changeMsg, duration * 2);
                            } else {
                                mHandlerInNewThread.sendMessageDelayed(changeMsg, duration * 2);
                            }
                        }
                        break;
                    case NumberPickerDefault.HANDLER_WHAT_LISTENER_VALUE_CHANGED:
                        respondPickedValueChanged(msg.arg1, msg.arg2, msg.obj);
                        break;
                }
            }
        };
        mHandlerInMainThread = new PickerInMainHandler(this);
    }


    private void respondPickedValueChangedInScrolling(int oldVal, int newVal) {
        mOnValueChangeListenerInScrolling.onValueChangeInScrolling(this, oldVal, newVal);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        updateMaxWHOfDisplayedValues(false);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mItemHeight = mViewHeight / mShowCount;
        mViewCenterX = ((float) (mViewWidth + getPaddingLeft() - getPaddingRight())) / 2;
        int defaultValue = 0;
        if (getOneRecycleSize() > 1) {
            if (mHasInit) {
                defaultValue = getValue() - mMinValue;
            } else if (mCurrentItemIndexEffect) {
                defaultValue = mCurrDrawFirstItemIndex + (mShowCount - 1) / 2;
            } else {
                defaultValue = 0;
            }
        }
        correctPositionByDefaultValue(defaultValue, mWrapSelectorWheel && mWrapSelectorWheelCheck);
        updateFontAttr();
        updateNotWrapYLimit();
        updateDividerAttr();
        mHasInit = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mHandlerThread == null || !mHandlerThread.isAlive()) {
            initHandler();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandlerThread.quit();
        //These codes are for dialog or PopupWindow which will be used for more than once.
        //Not an elegant solution, if you have any good idea, please let me know, thank you.
        if (mItemHeight == 0) return;
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
            mCurrDrawGlobalY = mScroller.getCurrY();
            calculateFirstItemParameterByGlobalY();
            if (mCurrDrawFirstItemY != 0) {
                if (mCurrDrawFirstItemY < (-mItemHeight / 2)) {
                    mCurrDrawGlobalY = mCurrDrawGlobalY + mItemHeight + mCurrDrawFirstItemY;
                } else {
                    mCurrDrawGlobalY = mCurrDrawGlobalY + mCurrDrawFirstItemY;
                }
                calculateFirstItemParameterByGlobalY();
            }
            onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE);
        }
        // see the comments on mRespondChangeOnDetach, if mRespondChangeOnDetach is false,
        // please initialize NumberPickerView's data every time setting up NumberPickerView,
        // set the demo of GregorianLunarCalendar
        int currPickedIndex = getWillPickIndexByGlobalY(mCurrDrawGlobalY);
        if (currPickedIndex != mPrevPickedIndex && mRespondChangeOnDetach) {
            try {
                if (mOnValueChangeListener != null) {
                    mOnValueChangeListener.onValueChange(NumberPickerView.this, mPrevPickedIndex + mMinValue, currPickedIndex + mMinValue);
                }
                if (mOnValueChangeListenerRaw != null) {
                    mOnValueChangeListenerRaw.onValueChangeRelativeToRaw(NumberPickerView.this, mPrevPickedIndex, currPickedIndex, mDisplayedValues);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mPrevPickedIndex = currPickedIndex;
    }

    /**
     * used by handlers to respond onchange callbacks
     *
     * @param oldVal        prevPicked value
     * @param newVal        currPicked value
     * @param respondChange if want to respond onchange callbacks
     */
    private void respondPickedValueChanged(int oldVal, int newVal, Object respondChange) {
        onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE);
        if (oldVal != newVal) {
            if (respondChange == null || !(respondChange instanceof Boolean) || (Boolean) respondChange) {
                if (mOnValueChangeListener != null) {
                    mOnValueChangeListener.onValueChange(NumberPickerView.this, oldVal + mMinValue, newVal + mMinValue);
                }
                if (mOnValueChangeListenerRaw != null) {
                    mOnValueChangeListenerRaw.onValueChangeRelativeToRaw(NumberPickerView.this, oldVal, newVal, mDisplayedValues);
                }
            }
        }
        mPrevPickedIndex = newVal;
        if (mPendingWrapToLinear) {
            mPendingWrapToLinear = false;
            internalSetWrapToLinear();
        }
    }

    private void scrollByIndexSmoothly(int deltaIndex) {
        scrollByIndexSmoothly(deltaIndex, true);
    }

    /**
     * @param deltaIndex  the delta index it will scroll by
     * @param needRespond need Respond to the ValueChange callback When Scrolling, default is false
     */
    private void scrollByIndexSmoothly(int deltaIndex, boolean needRespond) {
        if (!(mWrapSelectorWheel && mWrapSelectorWheelCheck)) {
            int willPickRawIndex = getPickedIndexRelativeToRaw();
            if (willPickRawIndex + deltaIndex > mMaxShowIndex) {
                deltaIndex = mMaxShowIndex - willPickRawIndex;
            } else if (willPickRawIndex + deltaIndex < mMinShowIndex) {
                deltaIndex = mMinShowIndex - willPickRawIndex;
            }
        }
        int duration;
        int dy;
        if (mCurrDrawFirstItemY < (-mItemHeight / 2)) {
            //scroll upwards for a distance of less than mItemHeight
            dy = mItemHeight + mCurrDrawFirstItemY;
            duration = (int) ((float) NumberPickerDefault.DEFAULT_INTERVAL_REVISE_DURATION * (mItemHeight + mCurrDrawFirstItemY) / mItemHeight);
            if (deltaIndex < 0) {
                duration = -duration - deltaIndex * NumberPickerDefault.DEFAULT_INTERVAL_REVISE_DURATION;
            } else {
                duration = duration + deltaIndex * NumberPickerDefault.DEFAULT_INTERVAL_REVISE_DURATION;
            }
        } else {
            //scroll downwards for a distance of less than mItemHeight
            dy = mCurrDrawFirstItemY;
            duration = (int) ((float) NumberPickerDefault.DEFAULT_INTERVAL_REVISE_DURATION * (-mCurrDrawFirstItemY) / mItemHeight);
            if (deltaIndex < 0) {
                duration = duration - deltaIndex * NumberPickerDefault.DEFAULT_INTERVAL_REVISE_DURATION;
            } else {
                duration = duration + deltaIndex * NumberPickerDefault.DEFAULT_INTERVAL_REVISE_DURATION;
            }
        }
        dy = dy + deltaIndex * mItemHeight;
        if (duration < NumberPickerDefault.DEFAULT_MIN_SCROLL_BY_INDEX_DURATION)
            duration = NumberPickerDefault.DEFAULT_MIN_SCROLL_BY_INDEX_DURATION;
        if (duration > NumberPickerDefault.DEFAULT_MAX_SCROLL_BY_INDEX_DURATION)
            duration = NumberPickerDefault.DEFAULT_MAX_SCROLL_BY_INDEX_DURATION;
        mScroller.startScroll(0, mCurrDrawGlobalY, 0, dy, duration);
        if (needRespond) {
            mHandlerInNewThread.sendMessageDelayed(NumberPickerUtils.getMsg(NumberPickerDefault.HANDLER_WHAT_REFRESH), duration / 4);
        } else {
            mHandlerInNewThread.sendMessageDelayed(NumberPickerUtils.getMsg(NumberPickerDefault.HANDLER_WHAT_REFRESH, 0, 0, false), duration / 4);
        }
        postInvalidate();
    }


    private void onScrollStateChange(int scrollState) {
        if (mScrollState == scrollState) {
            return;
        }
        mScrollState = scrollState;
        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChange(this, scrollState);
        }
    }


    private void internalSetWrapToLinear() {
        int rawIndex = getPickedIndexRelativeToRaw();
        correctPositionByDefaultValue(rawIndex - mMinShowIndex, false);
        mWrapSelectorWheel = false;
        postInvalidate();
    }

    private void updateDividerAttr() {
        mDividerIndex0 = mShowCount / 2;
        mDividerIndex1 = mDividerIndex0 + 1;
        dividerY0 = mDividerIndex0 * mViewHeight / mShowCount;
        dividerY1 = mDividerIndex1 * mViewHeight / mShowCount;
        if (mDividerMarginL < 0) mDividerMarginL = 0;
        if (mDividerMarginR < 0) mDividerMarginR = 0;

        if (mDividerMarginL + mDividerMarginR == 0) return;
        if (getPaddingLeft() + mDividerMarginL >= mViewWidth - getPaddingRight() - mDividerMarginR) {
            int surplusMargin = getPaddingLeft() + mDividerMarginL + getPaddingRight() + mDividerMarginR - mViewWidth;
            mDividerMarginL = (int) (mDividerMarginL - (float) surplusMargin * mDividerMarginL / (mDividerMarginL + mDividerMarginR));
            mDividerMarginR = (int) (mDividerMarginR - (float) surplusMargin * mDividerMarginR / (mDividerMarginL + mDividerMarginR));
        }
    }


    private void updateFontAttr() {
        if (mTextSizeNormal > mItemHeight) mTextSizeNormal = mItemHeight;
        if (mTextSizeSelected > mItemHeight) mTextSizeSelected = mItemHeight;

        if (mPaintHint == null) {
            throw new IllegalArgumentException("mPaintHint should not be null.");
        }
        mPaintHint.setTextSize(mTextSizeHint);
        mTextSizeHintCenterYOffset = getTextCenterYOffset(mPaintHint.getFontMetrics());
        mWidthOfHintText = getTextWidth(mHintText, mPaintHint);

        if (mPaintText == null) {
            throw new IllegalArgumentException("mPaintText should not be null.");
        }
        mPaintText.setTextSize(mTextSizeSelected);
        mTextSizeSelectedCenterYOffset = getTextCenterYOffset(mPaintText.getFontMetrics());
        mPaintText.setTextSize(mTextSizeNormal);
        mTextSizeNormalCenterYOffset = getTextCenterYOffset(mPaintText.getFontMetrics());
    }

    private void updateNotWrapYLimit() {
        mNotWrapLimitYTop = 0;
        mNotWrapLimitYBottom = -mShowCount * mItemHeight;
        if (mDisplayedValues != null) {
            mNotWrapLimitYTop = (getOneRecycleSize() - mShowCount / 2 - 1) * mItemHeight;
            mNotWrapLimitYBottom = -(mShowCount / 2) * mItemHeight;
        }
    }


    private int limitY(int currDrawGlobalYPreferred) {
        if (mWrapSelectorWheel && mWrapSelectorWheelCheck) return currDrawGlobalYPreferred;
        if (currDrawGlobalYPreferred < mNotWrapLimitYBottom) {
            currDrawGlobalYPreferred = mNotWrapLimitYBottom;
        } else if (currDrawGlobalYPreferred > mNotWrapLimitYTop) {
            currDrawGlobalYPreferred = mNotWrapLimitYTop;
        }
        return currDrawGlobalYPreferred;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mItemHeight == 0) return true;

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        currY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFlagMayPress = true;
                mHandlerInNewThread.removeMessages(NumberPickerDefault.HANDLER_WHAT_REFRESH);
                stopScrolling();
                downY = currY;
                downYGlobal = mCurrDrawGlobalY;
                onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE);
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float spanY = downY - currY;
                if (!(mFlagMayPress && (-mScaledTouchSlop < spanY && spanY < mScaledTouchSlop))) {
                    mFlagMayPress = false;
                    mCurrDrawGlobalY = limitY((int) (downYGlobal + spanY));
                    calculateFirstItemParameterByGlobalY();
                    invalidate();
                }
                onScrollStateChange(OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
                break;
            case MotionEvent.ACTION_UP:
                if (mFlagMayPress) {
                    click(event);
                } else {
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000);
                    int velocityY = (int) (velocityTracker.getYVelocity() * mFriction);
                    if (Math.abs(velocityY) > mMiniVelocityFling) {
                        mScroller.fling(0, mCurrDrawGlobalY, 0, -velocityY,
                                Integer.MIN_VALUE, Integer.MAX_VALUE, limitY(Integer.MIN_VALUE), limitY(Integer.MAX_VALUE));
                        invalidate();
                        onScrollStateChange(OnScrollListener.SCROLL_STATE_FLING);
                    }
                    mHandlerInNewThread.sendMessageDelayed(NumberPickerUtils.getMsg(NumberPickerDefault.HANDLER_WHAT_REFRESH), 0);
                    releaseVelocityTracker();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                downYGlobal = mCurrDrawGlobalY;
                stopScrolling();
                mHandlerInNewThread.sendMessageDelayed(NumberPickerUtils.getMsg(NumberPickerDefault.HANDLER_WHAT_REFRESH), 0);
                break;
        }
        return true;
    }

    private void click(MotionEvent event) {
        float y = event.getY();
        for (int i = 0; i < mShowCount; i++) {
            if (mItemHeight * i <= y && y < mItemHeight * (i + 1)) {
                clickItem(i);
                break;
            }
        }
    }

    private void clickItem(int showCountIndex) {
        if (0 <= showCountIndex && showCountIndex < mShowCount) {
            scrollByIndexSmoothly(showCountIndex - mShowCount / 2);
        }
    }

    private float getTextCenterYOffset(Paint.FontMetrics fontMetrics) {
        if (fontMetrics == null) {
            return 0;
        }
        return Math.abs(fontMetrics.top + fontMetrics.bottom) / 2;
    }


    //defaultPickedIndex relative to the shown part
    private void correctPositionByDefaultValue(int defaultPickedIndex, boolean wrap) {
        mCurrDrawFirstItemIndex = defaultPickedIndex - (mShowCount - 1) / 2;
        mCurrDrawFirstItemIndex = NumberPickerUtils.getIndexByRawIndex(mCurrDrawFirstItemIndex, getOneRecycleSize(), wrap);
        if (mItemHeight == 0) {
            mCurrentItemIndexEffect = true;
        } else {
            mCurrDrawGlobalY = mCurrDrawFirstItemIndex * mItemHeight;

            mInScrollingPickedOldValue = mCurrDrawFirstItemIndex + mShowCount / 2;
            mInScrollingPickedOldValue = mInScrollingPickedOldValue % getOneRecycleSize();
            if (mInScrollingPickedOldValue < 0) {
                mInScrollingPickedOldValue = mInScrollingPickedOldValue + getOneRecycleSize();
            }
            mInScrollingPickedNewValue = mInScrollingPickedOldValue;
            calculateFirstItemParameterByGlobalY();
        }
    }


    @Override
    public void computeScroll() {
        if (mItemHeight == 0) return;
        if (mScroller.computeScrollOffset()) {
            mCurrDrawGlobalY = mScroller.getCurrY();
            calculateFirstItemParameterByGlobalY();
            postInvalidate();
        }
    }

    private void calculateFirstItemParameterByGlobalY() {
        mCurrDrawFirstItemIndex = (int) Math.floor((float) mCurrDrawGlobalY / mItemHeight);
        mCurrDrawFirstItemY = -(mCurrDrawGlobalY - mCurrDrawFirstItemIndex * mItemHeight);
        if (mOnValueChangeListenerInScrolling != null) {
            if (-mCurrDrawFirstItemY > mItemHeight / 2) {
                mInScrollingPickedNewValue = mCurrDrawFirstItemIndex + 1 + mShowCount / 2;
            } else {
                mInScrollingPickedNewValue = mCurrDrawFirstItemIndex + mShowCount / 2;
            }
            mInScrollingPickedNewValue = mInScrollingPickedNewValue % getOneRecycleSize();
            if (mInScrollingPickedNewValue < 0) {
                mInScrollingPickedNewValue = mInScrollingPickedNewValue + getOneRecycleSize();
            }
            if (mInScrollingPickedOldValue != mInScrollingPickedNewValue) {
                respondPickedValueChangedInScrolling(mInScrollingPickedNewValue, mInScrollingPickedOldValue);
            }
            mInScrollingPickedOldValue = mInScrollingPickedNewValue;
        }
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void updateMaxWHOfDisplayedValues(boolean needRequestLayout) {
        updateMaxWidthOfDisplayedValues();
        updateMaxHeightOfDisplayedValues();
        if (needRequestLayout &&
                (mSpecModeW == MeasureSpec.AT_MOST || mSpecModeH == MeasureSpec.AT_MOST)) {
            mHandlerInMainThread.sendEmptyMessage(NumberPickerDefault.HANDLER_WHAT_REQUEST_LAYOUT);
        }
    }


    private int measureWidth(int measureSpec) {
        int result;
        int specMode = mSpecModeW = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            int marginOfHint = Math.max(mWidthOfHintText, mWidthOfAlterHint) == 0 ? 0 : mMarginEndOfHint;
            int gapOfHint = Math.max(mWidthOfHintText, mWidthOfAlterHint) == 0 ? 0 : mMarginStartOfHint;

            int maxWidth = Math.max(mMaxWidthOfAlterArrayWithMeasureHint,
                    Math.max(mMaxWidthOfDisplayedValues, mMaxWidthOfAlterArrayWithoutMeasureHint)
                            + 2 * (gapOfHint + Math.max(mWidthOfHintText, mWidthOfAlterHint) + marginOfHint + 2 * mItemPaddingHorizontal));
            result = this.getPaddingLeft() + this.getPaddingRight() + maxWidth;//MeasureSpec.UNSPECIFIED
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result;
        int specMode = mSpecModeH = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            int maxHeight = mShowCount * (mMaxHeightOfDisplayedValues + 2 * mItemPaddingVertical);
            result = this.getPaddingTop() + this.getPaddingBottom() + maxHeight;//MeasureSpec.UNSPECIFIED
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawContent(canvas);
        drawLine(canvas);
        drawHint(canvas);
    }

    private void drawContent(Canvas canvas) {
        int index;
        int textColor;
        float textSize;
        float fraction = 0f;
        float textSizeCenterYOffset;

        for (int i = 0; i < mShowCount + 1; i++) {
            float y = mCurrDrawFirstItemY + mItemHeight * i;
            index = NumberPickerUtils.getIndexByRawIndex(mCurrDrawFirstItemIndex + i, getOneRecycleSize(), mWrapSelectorWheel && mWrapSelectorWheelCheck);
            if (i == mShowCount / 2) {
                fraction = (float) (mItemHeight + mCurrDrawFirstItemY) / mItemHeight;
                textColor = NumberPickerUtils.getEvaluateColor(fraction, mTextColorNormal, mTextColorSelected);
                textSize = NumberPickerUtils.getEvaluateSize(fraction, mTextSizeNormal, mTextSizeSelected);
                textSizeCenterYOffset = NumberPickerUtils.getEvaluateSize(fraction, mTextSizeNormalCenterYOffset,
                        mTextSizeSelectedCenterYOffset);
            } else if (i == mShowCount / 2 + 1) {
                textColor = NumberPickerUtils.getEvaluateColor(1 - fraction, mTextColorNormal, mTextColorSelected);
                textSize = NumberPickerUtils.getEvaluateSize(1 - fraction, mTextSizeNormal, mTextSizeSelected);
                textSizeCenterYOffset = NumberPickerUtils.getEvaluateSize(1 - fraction, mTextSizeNormalCenterYOffset,
                        mTextSizeSelectedCenterYOffset);
            } else {
                textColor = mTextColorNormal;
                textSize = mTextSizeNormal;
                textSizeCenterYOffset = mTextSizeNormalCenterYOffset;
            }
            mPaintText.setColor(textColor);
            mPaintText.setTextSize(textSize);

            if (0 <= index && index < getOneRecycleSize()) {
                CharSequence str = mDisplayedValues[index + mMinShowIndex];
                if (mTextEllipsize != null) {
                    str = TextUtils.ellipsize(str, mPaintText, getWidth() - 2 * mItemPaddingHorizontal, NumberPickerUtils.getEllipsizeType(mTextEllipsize));
                }
                canvas.drawText(str.toString(), mViewCenterX,
                        y + mItemHeight / 2 + textSizeCenterYOffset, mPaintText);
            } else if (!TextUtils.isEmpty(mEmptyItemHint)) {
                canvas.drawText(mEmptyItemHint, mViewCenterX,
                        y + mItemHeight / 2 + textSizeCenterYOffset, mPaintText);
            }
        }
    }

    private void drawLine(Canvas canvas) {
        if (mShowDivider) {
            canvas.drawLine(getPaddingLeft() + mDividerMarginL,
                    dividerY0, mViewWidth - getPaddingRight() - mDividerMarginR, dividerY0, mPaintDivider);
            canvas.drawLine(getPaddingLeft() + mDividerMarginL,
                    dividerY1, mViewWidth - getPaddingRight() - mDividerMarginR, dividerY1, mPaintDivider);
        }
    }

    private void drawHint(Canvas canvas) {
        if (TextUtils.isEmpty(mHintText)) {
            return;
        }
        canvas.drawText(mHintText,
                mViewCenterX + (mMaxWidthOfDisplayedValues + mWidthOfHintText) / 2 + mMarginStartOfHint,
                (dividerY0 + dividerY1) / 2 + mTextSizeHintCenterYOffset, mPaintHint);
    }

    private void updateMaxWidthOfDisplayedValues() {
        float savedTextSize = mPaintText.getTextSize();
        mPaintText.setTextSize(mTextSizeSelected);
        mMaxWidthOfDisplayedValues = getMaxWidthOfTextArray(mDisplayedValues, mPaintText);
        mMaxWidthOfAlterArrayWithMeasureHint = getMaxWidthOfTextArray(mAlterTextArrayWithMeasureHint, mPaintText);
        mMaxWidthOfAlterArrayWithoutMeasureHint = getMaxWidthOfTextArray(mAlterTextArrayWithoutMeasureHint, mPaintText);
        mPaintText.setTextSize(mTextSizeHint);
        mWidthOfAlterHint = getTextWidth(mAlterHint, mPaintText);
        mPaintText.setTextSize(savedTextSize);
    }

    private void updateMaxHeightOfDisplayedValues() {
        float savedTextSize = mPaintText.getTextSize();
        mPaintText.setTextSize(mTextSizeSelected);
        mMaxHeightOfDisplayedValues = (int) (mPaintText.getFontMetrics().bottom - mPaintText.getFontMetrics().top + 0.5);
        mPaintText.setTextSize(savedTextSize);
    }

    private void updateContentAndIndex(String[] newDisplayedValues) {
        mMinShowIndex = 0;
        mMaxShowIndex = newDisplayedValues.length - 1;
        mDisplayedValues = newDisplayedValues;
        updateWrapStateByContent();
    }

    private void updateContent(String[] newDisplayedValues) {
        mDisplayedValues = newDisplayedValues;
        updateWrapStateByContent();
    }

    //used in setDisplayedValues
    private void updateValue() {
        inflateDisplayedValuesIfNull();
        updateWrapStateByContent();
        mMinShowIndex = 0;
        mMaxShowIndex = mDisplayedValues.length - 1;
    }

    private void updateValueForInit() {
        inflateDisplayedValuesIfNull();
        updateWrapStateByContent();
        if (mMinShowIndex == -1) {
            mMinShowIndex = 0;
        }
        if (mMaxShowIndex == -1) {
            mMaxShowIndex = mDisplayedValues.length - 1;
        }
        setMinAndMaxShowIndex(mMinShowIndex, mMaxShowIndex, false);
    }

    private void inflateDisplayedValuesIfNull() {
        if (mDisplayedValues == null) {
            mDisplayedValues = new String[1];
            mDisplayedValues[0] = "0";
        }
    }

    private void updateWrapStateByContent() {
        mWrapSelectorWheelCheck = mDisplayedValues.length > mShowCount;
    }

    private void stopRefreshing() {
        if (mHandlerInNewThread != null) {
            mHandlerInNewThread.removeMessages(NumberPickerDefault.HANDLER_WHAT_REFRESH);
        }
    }

    public void stopScrolling() {
        if (mScroller != null) {
            if (!mScroller.isFinished()) {
                mScroller.startScroll(0, mScroller.getCurrY(), 0, 0, 1);
                mScroller.abortAnimation();
                postInvalidate();
            }
        }
    }

    @Override
    public void onPickerInMainRequestLayout() {
        requestLayout();
    }

    @Override
    public void onPickerInMainValueChanged(int arg1, int arg2, Object obj) {
        respondPickedValueChanged(arg1, arg2, obj);
    }


    public void stopScrollingAndCorrectPosition() {
        stopScrolling();
        if (mHandlerInNewThread != null) {
            mHandlerInNewThread.sendMessageDelayed(NumberPickerUtils.getMsg(NumberPickerDefault.HANDLER_WHAT_REFRESH), 0);
        }
    }

    public void setOnScrollListener(OnScrollListener listener) {
        mOnScrollListener = listener;
    }

    public void setOnValueChangedListener(OnValueChangeListener listener) {
        mOnValueChangeListener = listener;
    }

    public void setOnValueChangedListenerRelativeToRaw(OnValueChangeListenerRelativeToRaw listener) {
        mOnValueChangeListenerRaw = listener;
    }

    public void setOnValueChangeListenerInScrolling(OnValueChangeListenerInScrolling listener) {
        mOnValueChangeListenerInScrolling = listener;
    }

    public void setContentTextTypeface(Typeface typeface) {
        mPaintText.setTypeface(typeface);
    }

    public void setHintTextTypeface(Typeface typeface) {
        mPaintHint.setTypeface(typeface);
    }

    public void setHintText(String hintText) {
        if (NumberPickerUtils.isStringEqual(mHintText, hintText)) {
            return;
        }
        mHintText = hintText;
        mTextSizeHintCenterYOffset = getTextCenterYOffset(mPaintHint.getFontMetrics());
        mWidthOfHintText = getTextWidth(mHintText, mPaintHint);
        mHandlerInMainThread.sendEmptyMessage(NumberPickerDefault.HANDLER_WHAT_REQUEST_LAYOUT);
    }

    public void setPickedIndexRelativeToMin(int pickedIndexToMin) {
        if (0 <= pickedIndexToMin && pickedIndexToMin < getOneRecycleSize()) {
            mPrevPickedIndex = pickedIndexToMin + mMinShowIndex;
            correctPositionByDefaultValue(pickedIndexToMin, mWrapSelectorWheel && mWrapSelectorWheelCheck);
            postInvalidate();
        }
    }

    public void setNormalTextColor(int normalTextColor) {
        if (mTextColorNormal == normalTextColor) return;
        mTextColorNormal = normalTextColor;
        postInvalidate();
    }

    public void setSelectedTextColor(int selectedTextColor) {
        if (mTextColorSelected == selectedTextColor) return;
        mTextColorSelected = selectedTextColor;
        postInvalidate();
    }

    public void setHintTextColor(int hintTextColor) {
        if (mTextColorHint == hintTextColor) return;
        mTextColorHint = hintTextColor;
        mPaintHint.setColor(mTextColorHint);
        postInvalidate();
    }

    public void setDividerColor(int dividerColor) {
        if (mDividerColor == dividerColor) return;
        mDividerColor = dividerColor;
        mPaintDivider.setColor(mDividerColor);
        postInvalidate();
    }

    public void setMinAndMaxShowIndex(int minShowIndex, int maxShowIndex) {
        setMinAndMaxShowIndex(minShowIndex, maxShowIndex, true);
    }

    public void setMinAndMaxShowIndex(int minShowIndex, int maxShowIndex, boolean needRefresh) {
        if (minShowIndex > maxShowIndex) {
            throw new IllegalArgumentException("minShowIndex should be less than maxShowIndex, minShowIndex is "
                    + minShowIndex + ", maxShowIndex is " + maxShowIndex + ".");
        }
        if (mDisplayedValues == null) {
            throw new IllegalArgumentException("mDisplayedValues should not be null, you need to set mDisplayedValues first.");
        } else {
            if (minShowIndex < 0) {
                throw new IllegalArgumentException("minShowIndex should not be less than 0, now minShowIndex is " + minShowIndex);
            } else if (minShowIndex > mDisplayedValues.length - 1) {
                throw new IllegalArgumentException("minShowIndex should not be greater than (mDisplayedValues.length - 1), now " +
                        "(mDisplayedValues.length - 1) is " + (mDisplayedValues.length - 1) + " minShowIndex is " + minShowIndex);
            }

            if (maxShowIndex < 0) {
                throw new IllegalArgumentException("maxShowIndex should not be less than 0, now maxShowIndex is " + maxShowIndex);
            } else if (maxShowIndex > mDisplayedValues.length - 1) {
                throw new IllegalArgumentException("maxShowIndex should not be greater than (mDisplayedValues.length - 1), now " +
                        "(mDisplayedValues.length - 1) is " + (mDisplayedValues.length - 1) + " maxShowIndex is " + maxShowIndex);
            }
        }
        mMinShowIndex = minShowIndex;
        mMaxShowIndex = maxShowIndex;
        if (needRefresh) {
            mPrevPickedIndex = mMinShowIndex;
            correctPositionByDefaultValue(0, mWrapSelectorWheel && mWrapSelectorWheelCheck);
            postInvalidate();
        }
    }

    /**
     * set the friction of scroller, it will effect the scroller's acceleration when fling
     *
     * @param friction default is ViewConfiguration.get(mContext).getScrollFriction()
     *                 if setFriction(2 * ViewConfiguration.get(mContext).getScrollFriction()),
     *                 the friction will be twice as much as before
     */
    public void setFriction(float friction) {
        if (friction <= 0)
            throw new IllegalArgumentException("you should set a a positive float friction, now friction is " + friction);
        mFriction = ViewConfiguration.getScrollFriction() / friction;
    }

    public void setDisplayedValuesAndPickedIndex(String[] newDisplayedValues, int pickedIndex, boolean needRefresh) {
        stopScrolling();
        if (newDisplayedValues == null) {
            throw new IllegalArgumentException("newDisplayedValues should not be null.");
        }
        if (pickedIndex < 0) {
            throw new IllegalArgumentException("pickedIndex should not be negative, now pickedIndex is " + pickedIndex);
        }
        updateContent(newDisplayedValues);
        updateMaxWHOfDisplayedValues(true);
        updateNotWrapYLimit();
        updateValue();
        mPrevPickedIndex = pickedIndex + mMinShowIndex;
        correctPositionByDefaultValue(pickedIndex, mWrapSelectorWheel && mWrapSelectorWheelCheck);
        if (needRefresh) {
            mHandlerInNewThread.sendMessageDelayed(NumberPickerUtils.getMsg(NumberPickerDefault.HANDLER_WHAT_REFRESH), 0);
            postInvalidate();
        }
    }

    public void setDisplayedValues(String[] newDisplayedValues, boolean needRefresh) {
        setDisplayedValuesAndPickedIndex(newDisplayedValues, 0, needRefresh);
    }

    /**
     * get the "fromValue" by using getValue(), if your picker's minValue is not 0,
     * make sure you can get the accurate value by getValue(), or you can use
     * smoothScrollToValue(int fromValue, int toValue, boolean needRespond)
     *
     * @param toValue the value you want picker to scroll to
     */
    public void smoothScrollToValue(int toValue) {
        smoothScrollToValue(getValue(), toValue, true);
    }

    /**
     * get the "fromValue" by using getValue(), if your picker's minValue is not 0,
     * make sure you can get the accurate value by getValue(), or you can use
     * smoothScrollToValue(int fromValue, int toValue, boolean needRespond)
     *
     * @param toValue     the value you want picker to scroll to
     * @param needRespond set if you want picker to respond onValueChange listener
     */
    public void smoothScrollToValue(int toValue, boolean needRespond) {
        smoothScrollToValue(getValue(), toValue, needRespond);
    }

    public void smoothScrollToValue(int fromValue, int toValue) {
        smoothScrollToValue(fromValue, toValue, true);
    }

    /**
     * @param fromValue   need to set the fromValue, can be greater than mMaxValue or less than mMinValue
     * @param toValue     the value you want picker to scroll to
     * @param needRespond need Respond to the ValueChange callback When Scrolling, default is false
     */
    public void smoothScrollToValue(int fromValue, int toValue, boolean needRespond) {
        int deltaIndex;
        fromValue = NumberPickerUtils.refineValueByLimit(fromValue, mMinValue, mMaxValue,
                mWrapSelectorWheel && mWrapSelectorWheelCheck, getOneRecycleSize());
        toValue = NumberPickerUtils.refineValueByLimit(toValue, mMinValue, mMaxValue,
                mWrapSelectorWheel && mWrapSelectorWheelCheck, getOneRecycleSize());
        if (mWrapSelectorWheel && mWrapSelectorWheelCheck) {
            deltaIndex = toValue - fromValue;
            int halfOneRecycleSize = getOneRecycleSize() / 2;
            if (deltaIndex < -halfOneRecycleSize || halfOneRecycleSize < deltaIndex) {
                deltaIndex = deltaIndex > 0 ? deltaIndex - getOneRecycleSize() : deltaIndex + getOneRecycleSize();
            }
        } else {
            deltaIndex = toValue - fromValue;
        }
        setValue(fromValue);
        if (fromValue == toValue) return;
        scrollByIndexSmoothly(deltaIndex, needRespond);
    }

    /**
     * simplify the "setDisplayedValue() + setMinValue() + setMaxValue()" process,
     * default minValue is 0, and make sure you do NOT change the minValue.
     *
     * @param display new values to be displayed
     */
    public void refreshByNewDisplayedValues(String[] display) {
        int minValue = getMinValue();

        int oldMaxValue = getMaxValue();
        int oldSpan = oldMaxValue - minValue + 1;

        int newMaxValue = display.length - 1;
        int newSpan = newMaxValue - minValue + 1;

        if (newSpan > oldSpan) {
            setDisplayedValues(display);
            setMaxValue(newMaxValue);
        } else {
            setMaxValue(newMaxValue);
            setDisplayedValues(display);
        }
    }

    public int getPickedIndexRelativeToRaw() {
        int willPickIndex;
        if (mCurrDrawFirstItemY != 0) {
            if (mCurrDrawFirstItemY < (-mItemHeight / 2)) {
                willPickIndex = getWillPickIndexByGlobalY(mCurrDrawGlobalY + mItemHeight + mCurrDrawFirstItemY);
            } else {
                willPickIndex = getWillPickIndexByGlobalY(mCurrDrawGlobalY + mCurrDrawFirstItemY);
            }
        } else {
            willPickIndex = getWillPickIndexByGlobalY(mCurrDrawGlobalY);
        }
        return willPickIndex;
    }

    public void setPickedIndexRelativeToRaw(int pickedIndexToRaw) {
        if (mMinShowIndex > -1) {
            if (mMinShowIndex <= pickedIndexToRaw && pickedIndexToRaw <= mMaxShowIndex) {
                mPrevPickedIndex = pickedIndexToRaw;
                correctPositionByDefaultValue(pickedIndexToRaw - mMinShowIndex, mWrapSelectorWheel && mWrapSelectorWheelCheck);
                postInvalidate();
            }
        }
    }

    public int getValue() {
        return getPickedIndexRelativeToRaw() + mMinValue;
    }

    public void setValue(int value) {
        if (value < mMinValue) {
            throw new IllegalArgumentException("should not set a value less than mMinValue, value is " + value);
        }
        if (value > mMaxValue) {
            throw new IllegalArgumentException("should not set a value greater than mMaxValue, value is " + value);
        }
        setPickedIndexRelativeToRaw(value - mMinValue);
    }

    public int getMinValue() {
        return mMinValue;
    }

    public void setMinValue(int minValue) {
        mMinValue = minValue;
        mMinShowIndex = 0;
        updateNotWrapYLimit();
    }

    public int getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(int maxValue) {
        if (mDisplayedValues == null) {
            throw new NullPointerException("mDisplayedValues should not be null");
        }
        if (maxValue - mMinValue + 1 > mDisplayedValues.length) {
            throw new IllegalArgumentException("(maxValue - mMinValue + 1) should not be greater than mDisplayedValues.length now " +
                    " (maxValue - mMinValue + 1) is " + (maxValue - mMinValue + 1) + " and mDisplayedValues.length is " + mDisplayedValues.length);
        }
        mMaxValue = maxValue;
        mMaxShowIndex = mMaxValue - mMinValue + mMinShowIndex;
        setMinAndMaxShowIndex(mMinShowIndex, mMaxShowIndex);
        updateNotWrapYLimit();
    }

    /**
     * 获取当前item的全部内容
     */
    public String[] getDisplayedValues() {
        return mDisplayedValues;
    }

    public void setDisplayedValues(String[] newDisplayedValues) {
        stopRefreshing();
        stopScrolling();
        if (newDisplayedValues == null) {
            throw new IllegalArgumentException("newDisplayedValues should not be null.");
        }

        if (mMaxValue - mMinValue + 1 > newDisplayedValues.length) {
            throw new IllegalArgumentException("mMaxValue - mMinValue + 1 should not be greater than mDisplayedValues.length, now "
                    + "((mMaxValue - mMinValue + 1) is " + (mMaxValue - mMinValue + 1)
                    + " newDisplayedValues.length is " + newDisplayedValues.length
                    + ", you need to set MaxValue and MinValue before setDisplayedValues(String[])");
        }
        updateContent(newDisplayedValues);
        updateMaxWHOfDisplayedValues(true);
        mPrevPickedIndex = mMinShowIndex;
        correctPositionByDefaultValue(0, mWrapSelectorWheel && mWrapSelectorWheelCheck);
        postInvalidate();
        mHandlerInMainThread.sendEmptyMessage(NumberPickerDefault.HANDLER_WHAT_REQUEST_LAYOUT);
    }

    public int getOneRecycleSize() {
        return mMaxShowIndex - mMinShowIndex + 1;
    }

    public int getRawContentSize() {
        if (mDisplayedValues != null)
            return mDisplayedValues.length;
        return 0;
    }

    public String getContentByCurrValue() {
        return mDisplayedValues[getValue() - mMinValue];
    }

    public boolean getWrapSelectorWheel() {
        return mWrapSelectorWheel;
    }

    public void setWrapSelectorWheel(boolean wrapSelectorWheel) {
        if (mWrapSelectorWheel != wrapSelectorWheel) {
            if (!wrapSelectorWheel) {
                if (mScrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                    internalSetWrapToLinear();
                } else {
                    mPendingWrapToLinear = true;
                }
            } else {
                mWrapSelectorWheel = true;
                updateWrapStateByContent();
                postInvalidate();
            }
        }
    }

    public boolean getWrapSelectorWheelAbsolutely() {
        return mWrapSelectorWheel && mWrapSelectorWheelCheck;
    }

    private int getMaxWidthOfTextArray(CharSequence[] array, Paint paint) {
        if (array == null) {
            return 0;
        }
        int maxWidth = 0;
        for (CharSequence item : array) {
            if (item != null) {
                int itemWidth = getTextWidth(item, paint);
                maxWidth = Math.max(itemWidth, maxWidth);
            }
        }
        return maxWidth;
    }

    private int getTextWidth(CharSequence text, Paint paint) {
        if (!TextUtils.isEmpty(text)) {
            return (int) (paint.measureText(text.toString()) + 0.5f);
        }
        return 0;
    }

    //return index relative to mDisplayedValues from 0.
    private int getWillPickIndexByGlobalY(int globalY) {
        if (mItemHeight == 0) return 0;
        int willPickIndex = globalY / mItemHeight + mShowCount / 2;
        int index = NumberPickerUtils.getIndexByRawIndex(willPickIndex, getOneRecycleSize(), mWrapSelectorWheel && mWrapSelectorWheelCheck);
        if (0 <= index && index < getOneRecycleSize()) {
            return index + mMinShowIndex;
        } else {
            throw new IllegalArgumentException("getWillPickIndexByGlobalY illegal index : " + index
                    + " getOneRecycleSize() : " + getOneRecycleSize() + " mWrapSelectorWheel : " + mWrapSelectorWheel);
        }
    }
}