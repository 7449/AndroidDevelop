package com.fiction.y.detail.w;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fiction.y.R;
import com.fiction.y.detail.m.DetailModel;
import com.fiction.y.detail.p.DetailPresenter;
import com.fiction.y.detail.p.DetailPresenterImpl;
import com.fiction.y.detail.v.DetailView;
import com.zzhoujay.richtext.RichText;

import framework.base.BaseActivity;
import framework.utils.UIUtils;
import rx.Observable;

/**
 * by y on 2017/1/8.
 */

public class DetailActivity extends BaseActivity implements DetailView, OnClickListener {
    private static final String URL = "url";

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView textView;
    private DetailPresenter presenter;
    private String onUrl = null;
    private String nextUrl = null;
    private NestedScrollView scrollView;

    public static void getInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        UIUtils.startActivity(DetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        presenter = new DetailPresenterImpl(this);
        Bundle extras = getIntent().getExtras();
        toolbar.setTitle(getString(R.string.detail_title));
        setSupportActionBar(toolbar);
        presenter.startDetail(extras.getString(URL));
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        progressBar = getView(R.id.progress);
        textView = getView(R.id.detail_tv);
        scrollView = getView(R.id.scroll_view);
        getView(R.id.btn_next).setOnClickListener(this);
        getView(R.id.btn_on).setOnClickListener(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void netWorkSuccess(DetailModel data) {
        onUrl = data.getOnPage();
        nextUrl = data.getNextPage();
        toolbar.setTitle(data.getTitle());
        scrollView.scrollTo(0, 0);
        RichText.fromHtml(data.getContent()).into(textView);
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
    public void viewBindToLifecycle(Observable<DetailModel> observable) {
        if (observable != null) {
            observable.compose(this.<DetailModel>bindToLifecycle());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (!TextUtils.isEmpty(nextUrl)) {
                    presenter.startDetail(nextUrl);
                }
                break;
            case R.id.btn_on:
                if (!TextUtils.isEmpty(onUrl)) {
                    presenter.startDetail(onUrl);
                }
                break;
        }
    }

}
