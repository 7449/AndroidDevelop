package com.jsoupsimple.image.imagedetail.widget;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;

import com.jsoupsimple.R;
import com.jsoupsimple.image.imagedetail.presenter.ImageDetailPresenter;
import com.jsoupsimple.image.imagedetail.presenter.ImageDetailPresenterImpl;
import com.jsoupsimple.image.imagedetail.view.ImageDetailView;

import java.util.LinkedList;
import java.util.List;

import framework.base.BaseDialogFragment;
import framework.base.BaseModel;
import framework.data.Constant;
import framework.utils.UIUtils;

/**
 * by y on 2016/8/3.
 */
public class ImageDetailFragment extends BaseDialogFragment
        implements ImageDetailView, ViewPager.OnPageChangeListener {
    private int page = 1;

    private ViewPager viewPager;
    private ProgressBar progressBar;

    private List<BaseModel> list;
    private ImageDetailAdapter adapter;
    private ImageDetailPresenter imageDetailPresenter;

    public static ImageDetailFragment newInstance(String url, String type) {
        ImageDetailFragment imageDetailFragment = new ImageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRAGMENT_URL, url);
        bundle.putString(FRAGMENT_TYPE, type);
        imageDetailFragment.setArguments(bundle);
        return imageDetailFragment;
    }

    @Override
    protected void initById() {
        viewPager = getView(R.id.viewPager);
        progressBar = getView(R.id.progress_bar);
    }

    @Override
    protected void initData() {
        if (bundle != null) {
            url = bundle.getString(FRAGMENT_URL);
            type = bundle.getString(FRAGMENT_TYPE);
        }
        list = new LinkedList<>();
        adapter = new ImageDetailAdapter(list);
        imageDetailPresenter = new ImageDetailPresenterImpl(this);
        viewPager.addOnPageChangeListener(this);
        getNetWork();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_detail;
    }


    @Override
    public void setData(List<BaseModel> datas) {
        if (!datas.isEmpty()) {
            list.addAll(datas);
        } else {
            return;
        }
        if (page == 1) {
            viewPager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        switch (type) {
            case Constant.M_ZI_TU_DETAIL:
                if (adapter.getCount() == 1) {
                    ++page;
                    getNetWork();
                }
                break;
        }
    }


    @Override
    public void netWorkError() {
        UIUtils.SnackBar(getActivity().findViewById(R.id.ll_layout), getString(R.string.network_error));
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (type) {
            case Constant.M_ZI_TU_DETAIL:
                if (position == adapter.getCount() - 1) {
                    ++page;
                    getNetWork();
                }
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void getNetWork() {
        imageDetailPresenter.netWorkRequest(url, page, type);
    }
}
