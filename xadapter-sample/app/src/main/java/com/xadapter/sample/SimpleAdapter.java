package com.xadapter.sample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.widget.SimpleLoadMore;
import com.xadapter.widget.XLoadMoreView;
import com.xadapter.widget.XRefreshView;

/**
 * by y.
 * <p>
 * Description:
 */
public class SimpleAdapter<T> extends XRecyclerViewAdapter<T> {

    public static final int TYPE_STATUS = 0;
    public static final int TYPE_REFRESH = 1;
    public static final int TYPE_LOAD_MORE = 2;

    private XLoadMoreView xLoadMoreView;

    @Override
    public XRecyclerViewAdapter<T> addRecyclerView(@NonNull RecyclerView recyclerView) {
        xLoadMoreView = new SimpleLoadMore(recyclerView.getContext());
        setLoadMoreView(xLoadMoreView);
        return super.addRecyclerView(recyclerView);
    }

    @Override
    public void onScrollBottom() {
        if (getLoadMoreState() != XLoadMoreView.LOAD) {
            super.onScrollBottom();
        }
    }

    @Override
    public void refreshState(int state) {
        super.refreshState(state);
        if (getRefreshState() == XRefreshView.ERROR) {
            xLoadMoreView.hideHeight(false);
        }
    }

    public void onComplete(int type) {
        if (type == TYPE_REFRESH) {
            refreshState(XRefreshView.SUCCESS);
        } else {
            loadMoreState(XLoadMoreView.SUCCESS);
        }
    }

    public void onError(int type) {
        if (type == TYPE_REFRESH) {
            refreshState(XRefreshView.ERROR);
        } else {
            loadMoreState(XLoadMoreView.ERROR);
        }
    }

    public void loadNoMore() {
        loadMoreState(XLoadMoreView.NOMORE);
    }

    public SimpleAdapter<T> onLoadMoreRetry(XLoadMoreRetryListener loadMoreRetryListener) {
        setFooterListener(view -> {
            if (getLoadMoreState() == XLoadMoreView.ERROR) {
                loadMoreState(XLoadMoreView.LOAD);
                loadMoreRetryListener.onLoadMoreRetry();
            }
        });
        return this;
    }
}
