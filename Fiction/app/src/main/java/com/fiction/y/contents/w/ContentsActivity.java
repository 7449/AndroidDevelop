package com.fiction.y.contents.w;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.fiction.y.R;
import com.fiction.y.contents.m.ContentsModel;
import com.fiction.y.contents.p.ContentsPresenterImpl;
import com.fiction.y.contents.v.ContentsView;
import com.fiction.y.detail.w.DetailActivity;
import com.xadapter.adapter.XBaseAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import framework.base.BaseActivity;
import framework.utils.UIUtils;
import rx.Observable;

/**
 * by y on 2017/1/8.
 */

public class ContentsActivity extends BaseActivity
        implements ContentsView, XBaseAdapter.OnXBindListener<ContentsModel>, XBaseAdapter.OnItemClickListener<ContentsModel> {

    private static final String URL = "url";
    private static final String TITLE = "title";

    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private XRecyclerViewAdapter<ContentsModel> xRecyclerViewAdapter;

    private List<ContentsModel> list;

    public static void getInstance(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TITLE, title);
        UIUtils.startActivity(ContentsActivity.class, bundle);
    }


    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        toolbar.setTitle(extras.getString(TITLE));
        setSupportActionBar(toolbar);
        swipeRefreshLayout.setEnabled(false);
        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        list = new ArrayList<>();
        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(list)
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.item_contents)
                        .onXBind(this)
                        .setOnItemClickListener(this)
        );
        new ContentsPresenterImpl(this).startContents(extras.getString(URL));
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        swipeRefreshLayout = getView(R.id.swipe);
        recyclerView = getView(R.id.recyclerView);
        progressBar = getView(R.id.progress);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contents;
    }


    @Override
    public void netWorkSuccess(List<ContentsModel> data) {
        Collections.reverse(data);
        xRecyclerViewAdapter.addAllData(data);
    }

    @Override
    public void netWorkError() {
        UIUtils.SnackBar(getView(R.id.rootView), getString(R.string.network_error));
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void viewBindToLifecycle(Observable<List<ContentsModel>> observable) {
        if (observable != null) {
            observable.compose(this.<List<ContentsModel>>bindToLifecycle());
        }
    }

    @Override
    public void onItemClick(View view, int position, ContentsModel info) {
        DetailActivity.getInstance(info.getDetailUrl(), info.getTitle());
    }

    @Override
    public void onXBind(XViewHolder holder, int position, ContentsModel contentsModel) {
        if (position < 10) {
            holder.setTextColor(R.id.contents_tv_, R.color.colorAccent);
        } else {
            holder.setTextColor(R.id.contents_tv_, R.color.colorBlack);
        }
        holder.setTextView(R.id.contents_tv_, contentsModel.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contents_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                Collections.reverse(list);
                xRecyclerViewAdapter.notifyDataSetChanged();
                break;
        }
        return true;
    }
}
