package com.fiction.y.search.w;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.fiction.y.R;
import com.fiction.y.contents.w.ContentsActivity;
import com.fiction.y.search.m.SearchModel;
import com.fiction.y.search.p.SearchPresenter;
import com.fiction.y.search.p.SearchPresenterImpl;
import com.fiction.y.search.v.SearchView;
import com.xadapter.adapter.XBaseAdapter;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import framework.base.BaseActivity;
import framework.db.GreenDaoDbUtils;
import framework.db.SqlBean;
import framework.utils.ImageLoaderUtils;
import framework.utils.UIUtils;
import framework.widget.FlowText;
import rx.Observable;

public class SearchActivity extends BaseActivity
        implements View.OnClickListener, SearchView, XBaseAdapter.OnXBindListener<SearchModel>, XBaseAdapter.OnItemClickListener<SearchModel>, XBaseAdapter.LoadingListener {

    private Toolbar mToolbar;
    private long exitTime = 0;
    private SearchPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private XRecyclerViewAdapter<SearchModel> xRecyclerViewAdapter;
    private EditText editText;
    private int page = 0;
    private String fictionName;
    private FlowText textView;


    @Override
    protected void initCreate(Bundle savedInstanceState) {
        mToolbar.setTitle(getString(R.string.toolbar_title));
        presenter = new SearchPresenterImpl(this);
        swipeRefreshLayout.setEnabled(false);
        xRecyclerViewAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(
                xRecyclerViewAdapter
                        .initXData(new ArrayList<SearchModel>())
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.item_search)
                        .onXBind(this)
                        .setOnItemClickListener(this)
                        .setLoadingListener(this)
        );
        mToolbar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                GreenDaoDbUtils.clear();
                UIUtils.SnackBar(getView(R.id.fa_btn), getString(R.string.db_clear));
                return true;
            }
        });
    }

    @Override
    protected void initById() {
        mToolbar = getView(R.id.toolbar);
        swipeRefreshLayout = getView(R.id.swipe);
        recyclerView = getView(R.id.recyclerView);
        progressBar = getView(R.id.progress);
        getView(R.id.fa_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fa_btn:
                startDialog();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            UIUtils.SnackBar(getView(R.id.fa_btn), getString(R.string.exit_app));
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void netWorkSuccess(List<SearchModel> data) {
        if (page == 0) {
            xRecyclerViewAdapter.setLoadingMoreEnabled(true);
        }
        xRecyclerViewAdapter.addAllData(data);
    }

    @Override
    public void netWorkError() {
        UIUtils.SnackBar(getView(R.id.fa_btn), getString(R.string.network_error));
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
    public void viewBindToLifecycle(Observable<List<SearchModel>> observable) {
        if (observable != null) {
            observable.compose(this.<List<SearchModel>>bindToLifecycle());
        }
    }


    private void startDialog() {
        List<SqlBean> fictionNameAll = GreenDaoDbUtils.getFictionNameAll();
        View view = View.inflate(getApplicationContext(), R.layout.dialog_search, null);
        editText = (EditText) view.findViewById(R.id.search_et);
        final FlowLayout flowLayout = (FlowLayout) view.findViewById(R.id.flow);
        flowLayout.removeAllViews();
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.fiction_name))
                .setView(view)
                .setNegativeButton(getString(R.string.dialog_unok), null)
                .setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fictionName = editText.getText().toString().trim();
                        startNetWork();
                    }
                }).create();
        alertDialog.show();
        for (int i = 0; i < fictionNameAll.size(); i++) {
            textView = new FlowText(flowLayout.getContext());
            textView.setText(fictionNameAll.get(i).getTitle());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FlowText flowText = (FlowText) v;
                    fictionName = flowText.getText().toString().trim();
                    startNetWork();
                    alertDialog.dismiss();
                }
            });
            flowLayout.addView(textView);
        }
    }

    private void startNetWork() {
        page = 0;
        xRecyclerViewAdapter.setLoadingMoreEnabled(false);
        xRecyclerViewAdapter.removeAll();
        presenter.startSearch(fictionName, page);
    }


    @Override
    public void fictionNameEmpty() {
        UIUtils.SnackBar(getView(R.id.fa_btn), getString(R.string.empty));
    }

    @Override
    public void onXBind(XViewHolder holder, int position, SearchModel searchModel) {
        ImageLoaderUtils.display(holder.getImageView(R.id.search_iv_img), searchModel.getImage());
        holder.setTextView(R.id.search_tv_title, searchModel.getTitle());
        holder.setTextView(R.id.search_tv_content, searchModel.getContent());
    }

    @Override
    public void onItemClick(View view, int position, SearchModel info) {
        ContentsActivity.getInstance(info.getDetailUrl(), info.getTitle());
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        if (editText != null) {
            ++page;
            presenter.startSearch(fictionName, page);
        }
    }

}
