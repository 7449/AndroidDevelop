package com.xadapter.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xadapter.holder.XViewHolder;
import com.xadapter.listener.LoadListener;
import com.xadapter.listener.OnXBindListener;

import java.util.List;

import io.reactivex.network.RxNetWork;

/**
 * @author y
 */
public class RefreshActivity extends AppCompatActivity
        implements OnXBindListener<Entity>, MainView, LoadListener {
    private MainPresenterImpl mainPresenter;
    private int page;
    private SimpleRefreshAdapter<Entity> mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxNetWork.getInstance().setBaseUrl(Api.BASE_URL);
        setContentView(R.layout.activity_refresh);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainPresenter = new MainPresenterImpl(this);

        mAdapter = (SimpleRefreshAdapter<Entity>) new SimpleRefreshAdapter<Entity>(swipeRefreshLayout)
                .addRecyclerView(recyclerView)
                .setLayoutId(R.layout.item_adapter)
                .setLoadingMoreEnabled(true)
                .setLoadListener(this)
                .onXBind(this);

        recyclerView.setAdapter(mAdapter);
        mainPresenter.onNetRequest(page, SimpleRefreshAdapter.TYPE_STATUS);
    }

    @Override
    public void onRefresh() {
        mainPresenter.onNetRequest(page = 0, SimpleRefreshAdapter.TYPE_REFRESH);
    }

    @Override
    public void onLoadMore() {
        mainPresenter.onNetRequest(page, SimpleRefreshAdapter.TYPE_LOAD_MORE);
    }

    @Override
    public void onXBind(XViewHolder holder, int position, Entity entity) {
        Glide
                .with(holder.getContext())
                .load(entity.getTitleImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.getImageView(R.id.list_image));
        holder.setTextView(R.id.list_tv, entity.getTitle());
        holder.setTextView(R.id.list_pos, String.valueOf(position));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxNetWork.getInstance().cancelAll();
    }


    @Override
    public void onChangeRootLayoutStatus(String status) {
        swipeRefreshLayout.setRefreshing(false);
        //don
    }

    @Override
    public void onRemoveAll() {
        mAdapter.removeAll();
    }

    @Override
    public void onNetComplete(int type) {
        mAdapter.onComplete(type);
    }

    @Override
    public void onNetError(int type) {
        mAdapter.onError(type);
    }

    @Override
    public void onLoadNoMore() {
        mAdapter.loadNoMore();
    }

    @Override
    public void onNetSuccess(List<Entity> entity) {
        mAdapter.addAllData(entity);
    }

    @Override
    public void onPagePlus() {
        page++;
    }

    @Override
    public int getPage() {
        return page;
    }
}
