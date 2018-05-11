package com.bilibilirecommend.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bilibilirecommend.main.model.RecommendCompat;


/**
 * by y on 2016/9/13
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<SuperViewHolder> {


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

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuperViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false));
    }

    protected abstract int getLayoutId(int viewType);

    @Override
    public void onViewAttachedToWindow(SuperViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams stagger = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            if (getItemViewType(holder.getLayoutPosition()) != RecommendCompat.TYPE_ITEM) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
    }
}