package com.dagger.mvp.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dagger.BaseActivity;
import com.dagger.R;
import com.dagger.application.App;
import com.dagger.mvp.model.MVPBean;
import com.dagger.mvp.model.RegisterModel;
import com.dagger.mvp.presenter.MVPPresenterImpl;
import com.dagger.mvp.view.DaggerMVPComponent;
import com.dagger.mvp.view.MVPComponent;
import com.dagger.mvp.view.MVPView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.network.manager.RxNetWork;

/**
 * by y on 2017/5/31.
 */

public class MVPActivity extends BaseActivity implements MVPView, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private MVPAdapter adapter;

    @Inject
    MVPPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });

        adapter = new MVPAdapter(new ArrayList<MVPBean>());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);


        MVPComponent build = DaggerMVPComponent.builder()
                .registerModel(new RegisterModel(this))
                .applicationComponent(App.getBuild((App) getApplicationContext()))
                .build();
        build.register(this);

        App application = build.getApplication();
    }

    @Override
    public void onRefresh() {
        presenter.startNetWork();
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNetError() {
        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetSuccess(List<MVPBean> list) {
        adapter.addAll(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxNetWork.getInstance().cancel(RxNetWork.TAG);
    }
}
