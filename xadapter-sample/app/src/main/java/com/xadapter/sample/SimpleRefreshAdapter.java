package com.xadapter.sample;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import com.xadapter.adapter.XBaseAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.listener.LoadListener;
import com.xadapter.widget.XLoadMoreView;

/**
 * by y.
 * <p>
 * Description:
 */
public class SimpleRefreshAdapter<T> extends XRecyclerViewAdapter<T> {

    public static final int TYPE_STATUS = 0;
    public static final int TYPE_REFRESH = 1;
    public static final int TYPE_LOAD_MORE = 2;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadListener loadListener;

    public SimpleRefreshAdapter(@NonNull SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (loadListener != null && getLoadMoreState() != XLoadMoreView.LOAD) {
                loadListener.onRefresh();
            }
        });
    }

    @Override
    public XBaseAdapter<T> setLoadListener(@NonNull LoadListener mLoadingListener) {
        loadListener = mLoadingListener;
        return super.setLoadListener(mLoadingListener);
    }

    @Override
    public void onScrollBottom() {
        if (getLoadMoreState() != XLoadMoreView.LOAD && !swipeRefreshLayout.isRefreshing()) {
            super.onScrollBottom();
        }
    }

    public void onComplete(int type) {
        if (type == TYPE_REFRESH) {
            swipeRefreshLayout.setRefreshing(false);
        } else {
            loadMoreState(XLoadMoreView.SUCCESS);
        }
    }

    public void onError(int type) {
        if (type == TYPE_REFRESH) {
            swipeRefreshLayout.setRefreshing(false);
        } else {
            loadMoreState(XLoadMoreView.ERROR);
        }
    }

    public void loadNoMore() {
        loadMoreState(XLoadMoreView.NOMORE);
    }

    public SimpleRefreshAdapter<T> onLoadMoreRetry(XLoadMoreRetryListener loadMoreRetryListener) {
        setFooterListener(view -> {
            if (getLoadMoreState() == XLoadMoreView.ERROR) {
                loadMoreState(XLoadMoreView.LOAD);
                loadMoreRetryListener.onLoadMoreRetry();
            }
        });
        return this;
    }
}
