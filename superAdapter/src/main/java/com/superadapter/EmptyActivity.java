package com.superadapter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2016/9/30
 */

public class EmptyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        View emptyView = findViewById(R.id.emptyView);
        List<DataModel> mDatas = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(mDatas, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setEmptyView(emptyView);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast("this is emptyView");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recyclerview_layout;
    }

}
