package com.superadapter;

import android.support.v7.widget.RecyclerView;

import com.superadapter.widget.SuperAdapter;
import com.superadapter.widget.SuperViewHolder;

import java.util.List;


/**
 * by y on 2016/9/30
 */

public class MyAdapter extends SuperAdapter<DataModel> {


    public MyAdapter(List<DataModel> mDatas, RecyclerView recyclerView) {
        super(mDatas, recyclerView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item;
    }

    @Override
    protected void onBind(SuperViewHolder holder, int position, DataModel data) {
        holder.setTextView(R.id.tv, data.getData());
    }

}
