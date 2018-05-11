package com.superadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.superadapter.widget.LoadMoreFooterLayout;
import com.superadapter.widget.RefreshHeaderLayout;
import com.superadapter.widget.SuperAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2016/9/30
 */

public class GridLayoutManagerActivity extends BaseActivity
        implements SuperAdapter.OnItemClickListener<DataModel>, SuperAdapter.LoadingListener {

    private MyAdapter adapter;
    private List<DataModel> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mDatas = new ArrayList<>();
        adapter = new MyAdapter(initData(), recyclerView);
        adapter.setLoadingListener(this);
        adapter.setOnItemClickListener(this);
        adapter.addHeader(getView(R.layout.header));
        adapter.addFooter(getView(R.layout.footer));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

        View headerView = adapter.getHeaderView();
        View footerView = adapter.getFooterView();
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast("this is footer");
            }
        });
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast("this is header");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recyclerview_layout;
    }


    @Override
    public void onItemClick(View view, int position, DataModel info) {
        Toast("onclick: position = " + position + "    data = " + info.data);
    }

    @Override
    public void onRefresh() {
        count = 0;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.removeAll();
                adapter.addAll(initData());
                adapter.refreshComplete(RefreshHeaderLayout.STATE_SUCCESS);
            }
        }, 1000);
    }

    private int count = 0;

    @Override
    public void onLoadMore() {
        count++;
        adapter.loadMoreComplete(LoadMoreFooterLayout.STATE_LOADING);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count > 2) {
                    adapter.loadMoreComplete(LoadMoreFooterLayout.STATE_LOAD_EMPTY);
                    return;
                }
                adapter.loadMoreComplete(LoadMoreFooterLayout.STATE_SUCCESS);
                adapter.addAll(initData());
            }
        }, 1000);
    }

    private List<DataModel> initData() {
        for (int i = 0; i < 20; i++) {
            mDatas.add(new DataModel("" + i));
        }
        return mDatas;
    }

}
