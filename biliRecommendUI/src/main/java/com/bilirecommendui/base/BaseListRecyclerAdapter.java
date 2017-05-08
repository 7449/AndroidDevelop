package com.bilirecommendui.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * by y on 2016/9/13
 */
public abstract class BaseListRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public List<T> mDatas = new LinkedList<>();

    public BaseListRecyclerAdapter(List<T> mDatas) {
        if (null != mDatas) {
            this.mDatas = mDatas;
        }
    }

    public View getView(ViewGroup viewGroup, int id) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
    }

    public void addAll(List<T> datas) {
        mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        this.notifyDataSetChanged();
    }

    public void removeAll() {
        if (mDatas.size() != 0) {
            mDatas.clear();
        }
        this.notifyDataSetChanged();
    }

    public int getDataCount() {
        if (null != mDatas) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuperViewHolder(getView(parent, getLayoutId()));
    }

    protected abstract int getLayoutId();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBind((SuperViewHolder) holder, position, mDatas.get(position));
    }

    protected abstract void onBind(SuperViewHolder viewHolder, int position, T mDatas);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

}