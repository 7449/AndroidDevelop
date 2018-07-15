package com.xadapter.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xadapter.holder.XViewHolder;
import com.xadapter.listener.LoadListener;
import com.xadapter.listener.OnXBindListener;
import com.xadapter.sample.status.StatusLayout;

import java.util.List;

import io.reactivex.network.RxNetWork;

public class MainActivity extends AppCompatActivity
        implements LoadListener, OnXBindListener<Entity>, MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SimpleAdapter<Entity> mAdapter;
    private StatusLayout statusLayout;
    private MainPresenterImpl mainPresenter;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxNetWork.getInstance().setBaseUrl(Api.BASE_URL);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        statusLayout = findViewById(R.id.layout_status);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new SimpleAdapter<>();
        mAdapter
                .onLoadMoreRetry(this::onLoadMore)
                .addRecyclerView(recyclerView)
                .setLayoutId(R.layout.item_adapter)
                .setPullRefreshEnabled(true)
                .setLoadingMoreEnabled(true)
                .onXBind(this)
                .setLoadListener(this);
        recyclerView.setAdapter(mAdapter);
        mainPresenter = new MainPresenterImpl(this);
        mainPresenter.onNetRequest(page, SimpleAdapter.TYPE_STATUS);
    }

    @Override
    public void onRefresh() {
        mainPresenter.onNetRequest(page = 0, SimpleAdapter.TYPE_REFRESH);
    }

    @Override
    public void onLoadMore() {
        mainPresenter.onNetRequest(page, SimpleAdapter.TYPE_LOAD_MORE);
    }

    @Override
    public void onChangeRootLayoutStatus(String status) {
        statusLayout.setStatus(status);
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
}
