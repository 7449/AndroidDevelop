package com.bilirecommendui.main.widget;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.bannerlayout.model.BannerModel;
import com.bilirecommendui.R;
import com.bilirecommendui.base.BaseActivity;
import com.bilirecommendui.main.model.RecommendModel;
import com.bilirecommendui.main.presenter.RecommendPresenter;
import com.bilirecommendui.main.presenter.RecommendPresenterImpl;
import com.bilirecommendui.main.view.RecommendView;
import com.bilirecommendui.main.widget.adapter.RecommendAdapter;

import java.util.List;

public class MainActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener, RecommendView {

    private LinearLayout rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecommendPresenter presenter;
    private RecommendAdapter adapter;
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        toolbar.setTitle("推荐");
        setSupportActionBar(toolbar);
        presenter = new RecommendPresenterImpl(this);

        adapter = new RecommendAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
    }

    @Override
    protected void initById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srf_layout);
        rootView = (LinearLayout) findViewById(R.id.activity_main);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onRefresh() {
        presenter.netWorkRequest(4);
    }

    @Override
    public void setBannerData(List<BannerModel> bannerData) {
        adapter.setBannerData(bannerData);
    }

    @Override
    public void setRecommendData(RecommendModel recommendData) {
        adapter.refreshData(recommendData);
    }

    @Override
    public void removeAdapter() {
        adapter.removeData();
    }

    @Override
    public void netWorkError() {
        Snackbar.make(rootView, "网络异常", Snackbar.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:
                break;
            case R.id.action_search:
                replaceTagFragment(ToolbarSearchFragment.startFragment(), ToolbarSearchFragment.TAG, ToolbarSearchFragment.BACK_STACK);
                break;
        }
        return true;
    }

    private void replaceTagFragment(Fragment fragment, String tag, String backStack) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, fragment, tag)
                .addToBackStack(backStack)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (baseFragment == null || !baseFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
