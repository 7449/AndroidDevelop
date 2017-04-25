package com.rv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rv.rv.filter.FilterAdapter;
import com.rv.rv.RVUtils;

import java.util.LinkedHashMap;

/**
 * by y on 2017/4/24
 */

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {


    private FilterAdapter filterAdapter;
    private AppCompatTextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3));
        filterAdapter = new FilterAdapter(RVUtils.initFilterData());
        recyclerView.setAdapter(filterAdapter);


        findViewById(R.id.ok).setOnClickListener(this);

        textView = (AppCompatTextView) findViewById(R.id.tv);
    }

    @Override
    public void onClick(View v) {
        LinkedHashMap<String, String> selectBean = filterAdapter.getSelectBean();


        textView.setText(selectBean.toString());
    }
}
