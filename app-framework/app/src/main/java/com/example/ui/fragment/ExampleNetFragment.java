package com.example.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.common.ImageLoaderUtils;
import com.common.widget.status.SimpleStatusClickListener;
import com.common.widget.status.StatusLayout;
import com.example.Api;
import com.example.R;
import com.example.entity.ExampleNetEntity;
import com.socks.library.KLog;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.network.RxNetWork;
import io.reactivex.network.RxNetWorkListener;

/**
 * by y.
 * <p>
 * Description:
 */

public class ExampleNetFragment extends BaseFragment
        implements RxNetWorkListener<List<ExampleNetEntity>>, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefresh;

    XRecyclerViewAdapter<ExampleNetEntity> mAdapter;

    @Override
    protected void initCreate(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRefresh.setOnRefreshListener(this);
        mRefresh.post(this::onRefresh);
        mAdapter = new XRecyclerViewAdapter<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(
                mAdapter
                        .setLayoutId(R.layout.item_example_net)
                        .onXBind((holder, position, exampleNetEntity) -> {
                            holder.setTextView(R.id.list_tv, exampleNetEntity.title);
                            ImageLoaderUtils.display(holder.getImageView(R.id.list_image), exampleNetEntity.titleImage);
                        })
        );
        mStatusLayout.setStatusClickListener(new SimpleStatusClickListener() {
            @Override
            public void onErrorClick() {
                super.onErrorClick();
                onRefresh();
            }
        });
        mStatusLayout.setErrorView(R.layout.layout_example_error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxNetWork.getInstance().cancel(this);
    }

    @Override
    public void onRefresh() {
        mAdapter.removeAll();
        mStatusLayout.setStatus(StatusLayout.Status.SUCCESS);
        RxNetWork.getInstance().cancel(this);
        RxNetWork.getInstance().getApi(RxNetWork.observable(Api.ZLService.class).getList("daily", 20, 0), this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_net_example;
    }

    @Override
    public void onNetWorkStart() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void onNetWorkError(Throwable e) {
        KLog.d(e.getMessage());
        mStatusLayout.setStatus(StatusLayout.Status.ERROR);
        mRefresh.setRefreshing(false);
    }

    @Override
    public void onNetWorkComplete() {
        mRefresh.setRefreshing(false);
        mStatusLayout.setStatus(StatusLayout.Status.SUCCESS);
    }

    @Override
    public void onNetWorkSuccess(List<ExampleNetEntity> data) {
        mAdapter.addAllData(data);
    }
}
