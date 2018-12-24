package com.xadapter.sample


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.xadapter.XLoadMoreView
import com.xadapter.adapter.XRecyclerViewAdapter

/**
 * by y.
 *
 *
 * Description:
 */
class SimpleRefreshAdapter<T>(private val swipeRefreshLayout: SwipeRefreshLayout) : XRecyclerViewAdapter<T>() {
    companion object {
        const val TYPE_STATUS = 0
        const val TYPE_REFRESH = 1
        const val TYPE_LOAD_MORE = 2
    }

    init {
        swipeRefreshLayout.setOnRefreshListener {
            if (xAdapterListener != null && loadMoreState != XLoadMoreView.LOAD) {
                xAdapterListener?.onXRefresh()
            }
        }
    }

    override fun onScrollBottom() {
        if (loadMoreState != XLoadMoreView.LOAD && !swipeRefreshLayout.isRefreshing) {
            super.onScrollBottom()
        }
    }

    fun onComplete(type: Int) {
        if (type == TYPE_REFRESH) {
            swipeRefreshLayout.isRefreshing = false
        } else {
            loadMoreState = XLoadMoreView.SUCCESS
        }
    }

    fun onError(type: Int) {
        if (type == TYPE_REFRESH) {
            swipeRefreshLayout.isRefreshing = false
        } else {
            loadMoreState = XLoadMoreView.ERROR
        }
    }

    fun loadNoMore() {
        loadMoreState = XLoadMoreView.NOMORE
    }
}
