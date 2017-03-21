package com.jsoupsimple.image.imagelist.widget;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jsoupsimple.R;
import com.jsoupsimple.image.imagedetail.widget.ImageDetailFragment;
import com.jsoupsimple.image.imagelist.presenter.ImagePresenter;
import com.jsoupsimple.image.imagelist.presenter.ImagePresenterImpl;
import com.jsoupsimple.image.imagelist.view.ImageView;
import com.jsoupsimple.image.imagelist.widget.adapter.ImageAdapter;
import com.jsoupsimple.image.video.VideoActivity;

import java.util.LinkedList;
import java.util.List;

import framework.base.BaseFragment;
import framework.base.BaseModel;
import framework.data.Constant;
import framework.network.NetWorkRequest;
import framework.utils.UIUtils;
import framework.widget.MRecyclerView;
import rx.Subscriber;

/**
 * by y on 2016/7/28.
 */
public class ImageFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, MRecyclerView.LoadingData, ImageView,
        ImageAdapter.OnItemClickListener<BaseModel> {

    protected int page = 1;

    private MRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ImageAdapter adapter;
    private ImagePresenter imageListPresenter;

    public static ImageFragment newInstance(int position, String type) {
        ImageFragment imageFragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        bundle.putString(FRAGMENT_TYPE, type);
        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Override
    protected void initBundle() {
        tabPosition = bundle.getInt(FRAGMENT_INDEX);
        type = bundle.getString(FRAGMENT_TYPE);
    }

    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.srf_layout);
    }

    @Override
    protected void initData() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        recyclerView.setLoadingData(this);
        recyclerView.setHasFixedSize(true);
        imageListPresenter = new ImagePresenterImpl(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new ImageAdapter(new LinkedList<BaseModel>());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });
        setLoad();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    public void onRefresh() {
        page = 1;
        getNetWork();
    }

    @Override
    public void onLoadMore() {
        ++page;
        getNetWork();
    }

    @Override
    public void setData(List<BaseModel> datas) {
        adapter.addAll(datas);
    }

    @Override
    public void netWorkError() {
        UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.network_error));
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
    public void removeAdapter() {
        adapter.removeAll();
    }

    @Override
    public void onItemClick(final View view, int position, BaseModel info) {
        if (type.equals(Constant.DOU_BAN_MEI_ZI) && tabPosition == 6) {
            getVideoUrl(info);
        } else {
            ImageDetailFragment.newInstance(info.getDetailUrl(), getImageDetailType())
                    .show(getChildFragmentManager(), getClass().getSimpleName());
        }
    }

    private void getNetWork() {
        switch (type) {
            case Constant.DOU_BAN_MEI_ZI:
                imageListPresenter.netWorkRequest((tabPosition + 2), page, type);
                break;
            default:
                imageListPresenter.netWorkRequest(tabPosition, page, type);
                break;
        }
    }

    private void getVideoUrl(BaseModel info) {
        NetWorkRequest.getVideo(info.getDetailUrl(), new Subscriber<BaseModel>() {

            @Override
            public void onStart() {
                super.onStart();
                showProgress();
            }

            @Override
            public void onCompleted() {
                hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                netWorkError();
                hideProgress();
            }

            @Override
            public void onNext(BaseModel videoModel) {
                VideoActivity.startActivity(videoModel.getUrl(), videoModel.getDetailUrl());
            }
        });
    }

    public String getImageDetailType() {
        switch (type) {
            case Constant.DOU_BAN_MEI_ZI:
                return Constant.DOU_BAN_MEI_ZI_DETAIL;
            case Constant.M_ZI_TU:
                return Constant.M_ZI_TU_DETAIL;
            default:
                return null;
        }
    }
}
