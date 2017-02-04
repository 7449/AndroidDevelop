package framework.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

/**
 * by y on 2016/11/8
 */

public class ReadRecyclerView extends RecyclerView {
    public ReadRecyclerView(Context context) {
        super(context);
    }

    public ReadRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReadRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    private LAYOUT_MANAGER_TYPE layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    private LoadingListener loadingListener;

    private View emptyView;

    private DataObserver mDataObserver = new DataObserver();


    public void init(int count, int type, Adapter adapter, LoadingListener loadingListener, View emptyView) {
        this.setHasFixedSize(true);
        this.setLayoutManager(new StaggeredGridLayoutManager(count, type));
        this.setAdapter(adapter);
        this.loadingListener = loadingListener;
        this.emptyView = emptyView;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mDataObserver);
        }
    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManagerType == null) {
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            } else if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }
    }


    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if (loadingListener != null && visibleItemCount > 0 && state == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == totalItemCount - 1) {
            loadingListener.onLoadMore();
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            isEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            isEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            isEmptyView();
        }
    }

    private void isEmptyView() {
        if (emptyView != null && getAdapter() != null) {
            boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    public interface LoadingListener {
        void onLoadMore();
    }
}
