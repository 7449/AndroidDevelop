package com.bilirecommendui.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * by y on 2016/9/13
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_ITEM = 3;

    public T mDatas = null;

    public void refreshData(T model) {
        this.mDatas = model;
        notifyDataSetChanged();
    }

    public void removeData() {
        if (null != mDatas) {
            mDatas = null;
        }
    }

    public boolean isNull() {
        return null == mDatas;
    }

    public View getView(ViewGroup viewGroup, int id) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
    }


    public boolean isBanner(int position) {
        return position == 0;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams stagger = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            if (getItemViewType(holder.getLayoutPosition()) != TYPE_ITEM) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
    }
}