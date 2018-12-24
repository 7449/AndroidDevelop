package com.common.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * by y on 2016/10/28
 */

public class LoadMoreRecyclerView extends RecyclerView {

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    private LAYOUT_MANAGER_TYPE layoutManagerType;
    private int[] lastPositions;
    private int lastVisibleItemPosition;
    private LoadingListener loadingListener;


    public void setLoadingListener(LoadingListener loadingListener) {
        this.loadingListener = loadingListener;
    }


    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        LayoutManager layoutManager = getLayoutManager();
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
        LayoutManager layoutManager = getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if (visibleItemCount > 0 && state == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == totalItemCount - 1 && loadingListener != null) {
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

    public interface LoadingListener {
        void onLoadMore();
    }
}
