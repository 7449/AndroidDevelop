@file:Suppress("MemberVisibilityCanBePrivate")

package com.status.layout

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.StringDef

val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER)

fun getViewLayout(statusLayout: StatusLayout, @LayoutRes id: Int): View = LayoutInflater.from(statusLayout.context).inflate(id, statusLayout, false)

fun goneView(vararg views: View?) {
    for (view in views) {
        if (view != null && view.visibility != View.GONE) {
            view.visibility = View.GONE
        }
    }
}

fun visibilityView(view: View?) {
    if (view != null && view.visibility != View.VISIBLE) {
        view.visibility = View.VISIBLE
    }
}

@StringDef(StatusLayout.NORMAL, StatusLayout.LOADING, StatusLayout.EMPTY, StatusLayout.SUCCESS, StatusLayout.ERROR)
@Retention(AnnotationRetention.SOURCE)
annotation class StatusAnnotation

interface OnStatusClickListener {
    fun onNorMalClick(view: View)
    fun onLoadingClick(view: View)
    fun onEmptyClick(view: View)
    fun onSuccessClick(view: View)
    fun onErrorClick(view: View)
}

open class SimpleOnStatusClickListener : OnStatusClickListener {
    override fun onNorMalClick(view: View) {}
    override fun onLoadingClick(view: View) {}
    override fun onEmptyClick(view: View) {}
    override fun onSuccessClick(view: View) {}
    override fun onErrorClick(view: View) {}
}

class StatusLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        const val NORMAL = "StatusLayout:Normal"
        const val LOADING = "StatusLayout:Loading"
        const val EMPTY = "StatusLayout:Empty"
        const val SUCCESS = "StatusLayout:Success"
        const val ERROR = "StatusLayout:Error"
    }

    private var mStatus: String = NORMAL
    var onStatusClickListener: OnStatusClickListener? = null
    private var mNorMalView: View? = null
    private var mLoadingView: View? = null
    private var mEmptyView: View? = null
    private var mSuccessView: View? = null
    private var mErrorView: View? = null

    init {
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
        goneView(mNorMalView, mEmptyView, mErrorView, mLoadingView, mSuccessView)
    }

    fun setStatus(@StatusAnnotation status: String): Boolean {
        if (TextUtils.equals(mStatus, status)) {
            return false
        }
        when (mStatus) {
            NORMAL -> goneView(mNorMalView)
            LOADING -> goneView(mLoadingView)
            EMPTY -> goneView(mEmptyView)
            SUCCESS -> goneView(mSuccessView)
            ERROR -> goneView(mErrorView)
            else -> throw RuntimeException("please check status")
        }
        when (status) {
            NORMAL -> visibilityView(mNorMalView)
            LOADING -> visibilityView(mLoadingView)
            EMPTY -> visibilityView(mEmptyView)
            SUCCESS -> visibilityView(mSuccessView)
            ERROR -> visibilityView(mErrorView)
            else -> throw RuntimeException("please check status")
        }
        mStatus = status
        return true
    }

    fun addNorMalView(norMalView: View): StatusLayout {
        addNorMalView(norMalView, params)
        return this
    }

    fun addLoadingView(loadingView: View): StatusLayout {
        addLoadingView(loadingView, params)
        return this
    }

    fun addEmptyView(emptyView: View): StatusLayout {
        addEmptyView(emptyView, params)
        return this
    }

    fun addSuccessView(successView: View): StatusLayout {
        addSuccessView(successView, params)
        return this
    }

    fun addErrorView(errorView: View): StatusLayout {
        addErrorView(errorView, params)
        return this
    }

    fun addNorMalView(@LayoutRes normalLayoutRes: Int): StatusLayout {
        addNorMalView(getViewLayout(this, normalLayoutRes))
        return this
    }

    fun addLoadingView(@LayoutRes loadingLayoutRes: Int): StatusLayout {
        addLoadingView(getViewLayout(this, loadingLayoutRes))
        return this
    }

    fun addEmptyView(@LayoutRes emptyLayoutRes: Int): StatusLayout {
        addEmptyView(getViewLayout(this, emptyLayoutRes))
        return this
    }

    fun addSuccessView(@LayoutRes successLayoutRes: Int): StatusLayout {
        addSuccessView(getViewLayout(this, successLayoutRes))
        return this
    }

    fun addErrorView(@LayoutRes errorLayoutRes: Int): StatusLayout {
        addErrorView(getViewLayout(this, errorLayoutRes))
        return this
    }

    fun addNorMalView(@LayoutRes normalLayoutRes: Int, params: FrameLayout.LayoutParams?): StatusLayout {
        addNorMalView(getViewLayout(this, normalLayoutRes), params)
        return this
    }

    fun addLoadingView(@LayoutRes loadingLayoutRes: Int, params: FrameLayout.LayoutParams?): StatusLayout {
        addLoadingView(getViewLayout(this, loadingLayoutRes), params)
        return this
    }

    fun addEmptyView(@LayoutRes emptyLayoutRes: Int, params: FrameLayout.LayoutParams?): StatusLayout {
        addEmptyView(getViewLayout(this, emptyLayoutRes), params)
        return this
    }

    fun addSuccessView(@LayoutRes successLayoutRes: Int, params: FrameLayout.LayoutParams?): StatusLayout {
        addSuccessView(getViewLayout(this, successLayoutRes), params)
        return this
    }

    fun addErrorView(@LayoutRes errorLayoutRes: Int, params: FrameLayout.LayoutParams?): StatusLayout {
        addErrorView(getViewLayout(this, errorLayoutRes), params)
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
        mNorMalView?.setOnClickListener { v -> onStatusClickListener?.onNorMalClick(v) }
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
        mLoadingView?.setOnClickListener { v -> onStatusClickListener?.onLoadingClick(v) }
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
        mEmptyView?.setOnClickListener { v -> onStatusClickListener?.onEmptyClick(v) }
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
        mSuccessView?.setOnClickListener { v -> onStatusClickListener?.onSuccessClick(v) }
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
        mErrorView?.setOnClickListener { v -> onStatusClickListener?.onErrorClick(v) }
        return this
    }

    @StatusAnnotation
    fun getStatus(): String {
        return mStatus
    }

    fun getView(@StatusAnnotation status: String): View? {
        return when (status) {
            NORMAL -> mNorMalView
            LOADING -> mLoadingView
            EMPTY -> mEmptyView
            SUCCESS -> mSuccessView
            ERROR -> mErrorView
            else -> throw RuntimeException("please check status")
        }
    }
}

