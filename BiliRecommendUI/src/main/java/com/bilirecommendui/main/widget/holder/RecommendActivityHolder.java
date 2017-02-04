package com.bilirecommendui.main.widget.holder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.bilirecommendui.R;
import com.bilirecommendui.base.SuperViewHolder;
import com.bilirecommendui.main.model.RecommendModel;
import com.bilirecommendui.main.widget.adapter.RecommendActivityAdapter;

import java.util.List;

/**
 * by y on 2016/9/19.
 */
public class RecommendActivityHolder extends SuperViewHolder {

    private RecyclerView mRecyclerView;

    public RecommendActivityHolder(View itemView) {
        super(itemView);
        mRecyclerView = get(R.id.recyclerView);
    }

    public void setData(List<RecommendModel.ResultBean.BodyBean> body) {
        if (null != body) {
            mRecyclerView.setHasFixedSize(true);
            RecommendActivityAdapter mAdapter = new RecommendActivityAdapter(body);
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
