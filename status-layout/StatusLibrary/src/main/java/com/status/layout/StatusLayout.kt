package com.status.layout


import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.LayoutRes
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 * by y on 14/07/2017.
 */

class StatusLayout : FrameLayout {

    private var mStatus: String = Status.NORMAL
    var onStatusClickListener: OnStatusClickListener? = null
    private var mNorMalView: View? = null
    private var mLoadingView: View? = null
    private var mEmptyView: View? = null
    private var mSuccessView: View? = null
    private var mErrorView: View? = null

    constructor(context: Context) : super(context) {
        initView(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatusLayout)
        val mNormalLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_normal_layout, View.NO_ID)
        val mLoadingLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_loading_layout, View.NO_ID)
        val mEmptyLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_empty_layout, View.NO_ID)
        val mSuccessLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_success_layout, View.NO_ID)
        val mErrorLayoutId = typedArray.getResourceId(R.styleable.StatusLayout_status_error_layout, View.NO_ID)
        typedArray.recycle()
        if (mNormalLayoutId != View.NO_ID) {
            addNorMalView(mNormalLayoutId)
        }
        if (mLoadingLayoutId != View.NO_ID) {
            addLoadingView(mLoadingLayoutId)
        }
        if (mEmptyLayoutId != View.NO_ID) {
            addEmptyView(mEmptyLayoutId)
        }
        if (mSuccessLayoutId != View.NO_ID) {
            addSuccessView(mSuccessLayoutId)
        }
        if (mErrorLayoutId != View.NO_ID) {
            addErrorView(mErrorLayoutId)
        }
        Util.goneView(mNorMalView, mEmptyView, mErrorView, mLoadingView, mSuccessView)
        setStatus(Status.SUCCESS)
    }

    fun setStatus(@Status.StatusAnnotation status: String): Boolean {
        if (TextUtils.equals(mStatus, status)) {
            return false
        }
        when (mStatus) {
            Status.NORMAL -> Util.goneView(mNorMalView)
            Status.LOADING -> Util.goneView(mLoadingView)
            Status.EMPTY -> Util.goneView(mEmptyView)
            Status.SUCCESS -> Util.goneView(mSuccessView)
            Status.ERROR -> Util.goneView(mErrorView)
            else -> throw RuntimeException("please check status")
        }
        when (status) {
            Status.NORMAL -> Util.visibilityView(mNorMalView)
            Status.LOADING -> Util.visibilityView(mLoadingView)
            Status.EMPTY -> Util.visibilityView(mEmptyView)
            Status.SUCCESS -> Util.visibilityView(mSuccessView)
            Status.ERROR -> Util.visibilityView(mErrorView)
            else -> throw RuntimeException("please check status")
        }
        mStatus = status
        return true
    }

    fun addNorMalView(norMalView: View): StatusLayout {
        addNorMalView(norMalView, Util.params)
        return this
    }

    fun addLoadingView(loadingView: View): StatusLayout {
        addLoadingView(loadingView, Util.params)
        return this
    }

    fun addEmptyView(emptyView: View): StatusLayout {
        addEmptyView(emptyView, Util.params)
        return this
    }

    fun addSuccessView(successView: View): StatusLayout {
        addSuccessView(successView, Util.params)
        return this
    }

    fun addErrorView(errorView: View): StatusLayout {
        addErrorView(errorView, Util.params)
        return this
    }

    fun addNorMalView(@LayoutRes normalLayoutRes: Int): StatusLayout {
        addNorMalView(Util.getViewLayout(this, normalLayoutRes))
        return this
    }

    fun addLoadingView(@LayoutRes loadingLayoutRes: Int): StatusLayout {
        addLoadingView(Util.getViewLayout(this, loadingLayoutRes))
        return this
    }

    fun addEmptyView(@LayoutRes emptyLayoutRes: Int): StatusLayout {
        addEmptyView(Util.getViewLayout(this, emptyLayoutRes))
        return this
    }

    fun addSuccessView(@LayoutRes successLayoutRes: Int): StatusLayout {
        addSuccessView(Util.getViewLayout(this, successLayoutRes))
        return this
    }

    fun addErrorView(@LayoutRes errorLayoutRes: Int): StatusLayout {
        addErrorView(Util.getViewLayout(this, errorLayoutRes))
        return this
    }

    fun addNorMalView(@LayoutRes normalLayoutRes: Int, params: FrameLayout.LayoutParams?): StatusLayout {
        addNorMalView(Util.getViewLayout(this, normalLayoutRes), params)
        return this
    }

    fun addLoadingView(@LayoutRes loadingLayoutRes: Int, params: FrameLayout.LayoutParams?): StatusLayout {
        addLoadingView(Util.getViewLayout(this, loadingLayoutRes), params)
        return this
    }

    fun addEmptyView(@LayoutRes emptyLayoutRes: Int, params: FrameLayout.LayoutParams?): StatusLayout {
        addEmptyView(Util.getViewLayout(this, emptyLayoutRes), params)
        return this
    }

    fun addSuccessView(@LayoutRes successLayoutRes: Int, params: FrameLayout.LayoutParams?): StatusLayout {
        addSuccessView(Util.getViewLayout(this, successLayoutRes), params)
        return this
    }

    fun addErrorView(@LayoutRes errorLayoutRes: Int, params: FrameLayout.LayoutParams?): StatusLayout {
        addErrorView(Util.getViewLayout(this, errorLayoutRes), params)
        return this
    }

    fun addNorMalView(norMalView: View, params: FrameLayout.LayoutParams?): StatusLayout {
        if (mNorMalView != null) {
            removeView(mNorMalView)
            mNorMalView = null
        }
        this.mNorMalView = norMalView
        if (params != null) {
            addView(norMalView, params)
        } else {
            addView(norMalView)
        }
        mNorMalView?.setOnClickListener { v ->
            if (onStatusClickListener != null)
                onStatusClickListener!!.onNorMalClick(v)
        }
        return this
    }

    fun addLoadingView(loadingView: View, params: FrameLayout.LayoutParams?): StatusLayout {
        if (mLoadingView != null) {
            removeView(mLoadingView)
            mLoadingView = null
        }
        this.mLoadingView = loadingView
        if (params != null) {
            addView(loadingView, params)
        } else {
            addView(loadingView)
        }
        mLoadingView?.setOnClickListener { v ->
            if (onStatusClickListener != null)
                onStatusClickListener!!.onLoadingClick(v)
        }
        return this
    }

    fun addEmptyView(emptyView: View, params: FrameLayout.LayoutParams?): StatusLayout {
        if (mEmptyView != null) {
            removeView(mEmptyView)
            mEmptyView = null
        }
        this.mEmptyView = emptyView
        if (params != null) {
            addView(emptyView, params)
        } else {
            addView(emptyView)
        }
        mEmptyView?.setOnClickListener { v ->
            if (onStatusClickListener != null)
                onStatusClickListener!!.onEmptyClick(v)
        }
        return this
    }

    fun addSuccessView(successView: View, params: FrameLayout.LayoutParams?): StatusLayout {
        if (mSuccessView != null) {
            removeView(mSuccessView)
            mSuccessView = null
        }
        this.mSuccessView = successView
        if (params != null) {
            addView(successView, params)
        } else {
            addView(successView)
        }
        mSuccessView?.setOnClickListener { v ->
            if (onStatusClickListener != null)
                onStatusClickListener!!.onSuccessClick(v)
        }
        return this
    }

    fun addErrorView(errorView: View, params: FrameLayout.LayoutParams?): StatusLayout {
        if (mErrorView != null) {
            removeView(mErrorView)
            mErrorView = null
        }
        this.mErrorView = errorView
        if (params != null) {
            addView(errorView, params)
        } else {
            addView(errorView)
        }
        mErrorView?.setOnClickListener { v ->
            if (onStatusClickListener != null)
                onStatusClickListener!!.onErrorClick(v)
        }
        return this
    }

    @Status.StatusAnnotation
    fun getStatus(): String {
        return mStatus
    }

    fun getView(@Status.StatusAnnotation status: String): View {
        return when (status) {
            Status.NORMAL -> mNorMalView!!
            Status.LOADING -> mLoadingView!!
            Status.EMPTY -> mEmptyView!!
            Status.SUCCESS -> mSuccessView!!
            Status.ERROR -> mErrorView!!
            else -> throw RuntimeException("please check status")
        }
    }
}

