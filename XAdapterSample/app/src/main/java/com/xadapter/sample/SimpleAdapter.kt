package com.xadapter.sample

import com.xadapter.XLoadMoreView
import com.xadapter.XRefreshView
import com.xadapter.adapter.XRecyclerViewAdapter

/**
 * by y.
 *
 *
 * Description:
 */
class SimpleAdapter<T> : XRecyclerViewAdapter<T>() {
    companion object {
        const val TYPE_STATUS = 0
        const val TYPE_REFRESH = 1
        const val TYPE_LOAD_MORE = 2
    }

    fun onComplete(type: Int) {
        if (type == TYPE_REFRESH) {
            refreshState = XRefreshView.SUCCESS
        } else {
            loadMoreState = XLoadMoreView.SUCCESS
        }
    }

    fun onError(type: Int) {
        if (type == TYPE_REFRESH) {
            refreshState = XRefreshView.ERROR
        } else {
            loadMoreState = XLoadMoreView.ERROR
        }
    }

    fun loadNoMore() {
        loadMoreState = XLoadMoreView.NOMORE
    }
}
