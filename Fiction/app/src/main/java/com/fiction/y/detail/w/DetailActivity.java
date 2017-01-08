package com.fiction.y.detail.w;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fiction.y.R;
import com.fiction.y.detail.m.DetailModel;
import com.fiction.y.detail.p.DetailPresenterImpl;
import com.fiction.y.detail.v.DetailView;

import framework.base.BaseActivity;
import framework.utils.UIUtils;
import rx.Observable;

/**
 * by y on 2017/1/8.
 */

public class DetailActivity extends BaseActivity implements DetailView {
    private static final String URL = "url";
    private static final String TITLE = "title";

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView textView;

    public static void getInstance(String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TITLE, title);
        UIUtils.startActivity(DetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        toolbar.setTitle(extras.getString(TITLE));
        setSupportActionBar(toolbar);
        new DetailPresenterImpl(this).startDetail(extras.getString(URL));
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        progressBar = getView(R.id.progress);
        textView = getView(R.id.detail_tv);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void netWorkSuccess(DetailModel data) {
        textView.setText(Html.fromHtml(data.getContent()));
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
}
