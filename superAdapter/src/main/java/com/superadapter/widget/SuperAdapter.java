package com.superadapter.widget;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * by y on 2016/9/29
 */

public abstract class SuperAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements RecyclerOnScrollListener.LoadingData, RecyclerOnTouchListener.RefreshInterface {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_REFRESH_FOOTER = 3;
    private static final int TYPE_REFRESH_HEADER = 4;

    private List<T> mDatas = new LinkedList<>();
    private LoadingListener mLoadingListener = null;
    private OnItemClickListener<T> mOnItemClickListener = null;
    private OnItemLongClickListener<T> mOnLongClickListener = null;
    private LoadMoreFooterLayout loadMoreFooterLayout = null;
    private RefreshHeaderLayout refreshHeaderLayout = null;
    private View emptyView = null;
    private RecyclerView recyclerView = null;
    private View mFooterView = null;
    private View mHeaderView = null;
    private boolean isRefreshHeader = true;
    private boolean isRefreshFooter = true;

    public SuperAdapter(List<T> mDatas, RecyclerView recyclerView) {
        if (null != mDatas) {
            this.mDatas = mDatas;
        }
        if (null != recyclerView) {
            this.recyclerView = recyclerView;
            if (isRefreshHeader) {
                refreshHeaderLayout = new RefreshHeaderLayout(recyclerView.getContext());
            }
            recyclerView.addOnScrollListener(new RecyclerOnScrollListener(this));
            recyclerView.setOnTouchListener(new RecyclerOnTouchListener(refreshHeaderLayout, isRefreshHeader, this));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_REFRESH_HEADER:
                return new SuperViewHolder(refreshHeaderLayout);
            case TYPE_REFRESH_FOOTER:
                loadMoreFooterLayout = new LoadMoreFooterLayout(parent.getContext());
                return new SuperViewHolder(loadMoreFooterLayout);
            case TYPE_FOOTER:
                return new SuperViewHolder(getFooterView());
            case TYPE_HEADER:
                return new SuperViewHolder(getHeaderView());
            default:
                return new SuperViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false));
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) != TYPE_ITEM) {
            return;
        }
        final int pos = getItemPosition(position);
        final T data = mDatas.get(pos);
        SuperViewHolder viewHolder = (SuperViewHolder) holder;
        onBind(viewHolder, pos, data);
        if (null != mOnItemClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, pos, data);
                }
            });
        }
        if (null != mOnLongClickListener) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnLongClickListener.onLongClick(v, pos, data);
                    return true;
                }
            });
        }
    }


    public void setEmptyView(View view) {
        if (null != view) {
            this.emptyView = view;
            ObserverEmptyView();
        }
    }

    public void setRefreshing(boolean refreshing) {
        if (refreshing && isRefreshHeader && null != mLoadingListener) {
            refreshHeaderLayout.setStates(RefreshHeaderLayout.STATE_LOADING);
            refreshHeaderLayout.refreshUI(refreshHeaderLayout.getMeasuredHeight());
            mLoadingListener.onRefresh();
        }
    }


    public View getFooterView() {
        return mFooterView;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public boolean isRefreshHeader() {
        return isRefreshHeader;
    }

    public void setRefreshHeader(boolean refreshHeader) {
        if (refreshHeader == this.isRefreshHeader) {
            return;
        }
        isRefreshHeader = refreshHeader;
    }

    public boolean isRefreshFooter() {
        return isRefreshFooter;
    }

    public void setRefreshFooter(boolean refreshFooter) {
        if (refreshFooter == this.isRefreshFooter) {
            return;
        }
        isRefreshFooter = refreshFooter;
    }

    public void addHeader(View headerView) {
        this.mHeaderView = headerView;
    }

    public void addFooter(View footerView) {
        this.mFooterView = footerView;
    }

    public void refreshComplete(int type) {
        if (null != refreshHeaderLayout) {
            refreshHeaderLayout.refreshComplete(type);
        }
    }

    public void loadMoreComplete(int type) {
        if (null != loadMoreFooterLayout) {
            loadMoreFooterLayout.loadMoreComplete(type);
        }
    }

    public void setLoadMoreFooterImage(int id) {
        if (null != loadMoreFooterLayout) {
            loadMoreFooterLayout.setImage(id);
        }
    }

    public void addAll(List<T> datas) {
        if (null != datas) {
            mDatas.addAll(datas);
            ObserverEmptyView();
        }
    }

    public void remove(int position) {
        if (null != mDatas) {
            mDatas.remove(position);
        }
    }

    public void removeAll() {
        if (null != mDatas && mDatas.size() != 0) {
            mDatas.clear();
        }
    }

    public interface LoadingListener {
        void onRefresh();

        void onLoadMore();
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T info);
    }

    public interface OnItemLongClickListener<T> {
        void onLongClick(View view, int position, T info);
    }

    public void setLoadingListener(LoadingListener mLoadingListener) {
        this.mLoadingListener = mLoadingListener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnLongClickListener = listener;
    }

    private void ObserverEmptyView() {
        if (null != emptyView && null != recyclerView && null != mDatas) {
            if (mDatas.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        }
    }

    private boolean isRefreshHeader(int position) {
        return position == 0 && isRefreshHeader;
    }

    private boolean isRefreshFooter(int position) {
        return position == getItemCount() - 1 && isRefreshFooter;
    }

    private boolean isFooter(int position) {
        if (isRefreshHeader) {
            return position > (mDatas.size() + getViewCount(mHeaderView)) && !isRefreshFooter(position) && isFooterView();
        }
        return position >= (mDatas.size() + getViewCount(mHeaderView)) && !isRefreshFooter(position) && isFooterView();
    }

    private boolean isHeader(int position) {
        if (isRefreshHeader) {
            return position >= 1 && position <= getViewCount(mHeaderView) && isHeaderView();
        }
        return position < getViewCount(mHeaderView) && isHeaderView();
    }


    @Override
    public void onLoadMore() {
        if (null != mLoadingListener) {
            mLoadingListener.onLoadMore();
        }
    }

    @Override
    public void onRefresh() {
        if (null != mLoadingListener) {
            mLoadingListener.onRefresh();
        }
    }

    @Override
    public int getItemCount() {
        int tempPosition;
        if (isRefreshHeader && isRefreshFooter) {
            tempPosition = 2;
        } else if (isRefreshHeader || isRefreshFooter) {
            tempPosition = 1;
        } else {
            tempPosition = 0;
        }
        return mDatas.size() + getViewCount(mHeaderView) + getViewCount(mFooterView) + tempPosition;
    }

    @Override
    public int getItemViewType(int position) {
        if (isRefreshHeader(position)) {
            return TYPE_REFRESH_HEADER;
        }
        if (isHeader(position)) {
            return TYPE_HEADER;
        }
        if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        if (isRefreshFooter(position)) {
            return TYPE_REFRESH_FOOTER;
        }
        return TYPE_ITEM;
    }


    private int getItemPosition(int position) {
        if (isRefreshHeader) {
            position -= 1;
        }
        return position - getViewCount(mHeaderView);
    }

    private int getViewCount(View view) {
        if (view == null) {
            return 0;
        } else {
            return 1;
        }
    }

    private boolean isFooterView() {
        return null != mFooterView;
    }

    private boolean isHeaderView() {
        return null != mHeaderView;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (!(getItemViewType(position) == TYPE_ITEM)) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams stagger = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            if (!(getItemViewType(holder.getLayoutPosition()) == TYPE_ITEM)) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
    }

    protected abstract int getLayoutId();

    protected abstract void onBind(SuperViewHolder holder, int position, T data);

}
