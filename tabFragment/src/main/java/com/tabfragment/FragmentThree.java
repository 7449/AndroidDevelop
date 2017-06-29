package com.tabfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bannerlayout.widget.BannerLayout;
import com.bumptech.glide.Glide;
import com.xadapter.LoadListener;
import com.xadapter.OnXBindListener;
import com.xadapter.adapter.XRecyclerViewAdapter;
import com.xadapter.holder.XViewHolder;
import com.xadapter.widget.FooterLayout;
import com.xadapter.widget.HeaderLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.network.manager.RxNetWork;
import io.reactivex.network.manager.RxNetWorkListener;

/**
 * by y on 2016/10/5.
 */

public class FragmentThree extends BaseFragment implements OnXBindListener<Bean>, LoadListener, RxNetWorkListener<List<Bean>> {

    private RecyclerView recyclerView;
    private AppCompatImageView emptyView;

    private BannerLayout bannerLayout;

    private XRecyclerViewAdapter<Bean> mAdapter = new XRecyclerViewAdapter<>();

    private int page = 0;


    public static FragmentThree startFragment() {
        return new FragmentThree();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_three, null);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        emptyView = (AppCompatImageView) inflate.findViewById(R.id.emptyView);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_banner, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        bannerLayout = (BannerLayout) inflate.findViewById(R.id.banner);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(
                mAdapter
                        .setEmptyView(emptyView)
                        .addRecyclerView(recyclerView)
                        .setLayoutId(R.layout.item)
                        .setPullRefreshEnabled(true)
                        .addHeaderView(inflate)
                        .onXBind(this)
                        .setLoadListener(this)
                        .setRefreshing(true)
        );

    }

    @Override
    public void onRefresh() {
        mAdapter.removeAll();
        netWork();
    }

    @Override
    public void onLoadMore() {
    }

    private void netWork() {
        RxNetWork.getInstance().cancel(getClass().getSimpleName());
        RxNetWork
                .getInstance()
                .setBaseUrl(NetApi.ZL_BASE_API)
                .getApi(getClass().getSimpleName(),
                        RxNetWork.observable(NetApi.ZLService.class)
                                .getList("daily", 20, 0), this);
    }

    @Override
    public void onXBind(XViewHolder holder, int position, Bean bean) {
        Glide
                .with(holder.getContext())
                .load(bean.getTitleImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.getImageView(R.id.list_image));
        holder.setTextView(R.id.list_tv, bean.getTitle());
    }

    @Override
    public void onNetWorkStart() {
    }

    @Override
    public void onNetWorkError(Throwable e) {
        if (page == 0) {
            mAdapter.refreshComplete(HeaderLayout.STATE_ERROR);
        } else {
            mAdapter.loadMoreComplete(FooterLayout.STATE_ERROR);
        }
    }

    @Override
    public void onNetWorkComplete() {
        if (page == 0) {
            mAdapter.refreshComplete(HeaderLayout.STATE_DONE);
        } else {
            mAdapter.loadMoreComplete(FooterLayout.STATE_COMPLETE);
        }
    }


    @Override
    public void onNetWorkSuccess(List<Bean> data) {
        mAdapter.addAllData(data);
        if (page == 0) {
            bannerLayout
                    .initTips()
                    .initListResources(initModel())
                    .switchBanner(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxNetWork.getInstance().cancel(getClass().getSimpleName());
    }

    private List<BannerModel> initModel() {
        List<BannerModel> mDatas = new ArrayList<>();
        mDatas.add(new BannerModel("http://ww2.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6kxwh0j30dw099ta3.jpg", "At that time just love, this time to break up"));
        mDatas.add(new BannerModel("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491588490192&di=c7c9dfd2fc4b1eeb5a4a874ec9a30d1d&imgtype=0&src=http%3A%2F%2Fmvimg2.meitudata.com%2F55713dd0165c89055.jpg"));
        mDatas.add(new BannerModel("http://ww1.sinaimg.cn/bmiddle/0060lm7Tgw1f94c6f7f26j30dw0ii76k.jpg", "The legs are not long but thin"));
        mDatas.add(new BannerModel("http://ww4.sinaimg.cn/bmiddle/0060lm7Tgw1f94c63dfjxj30dw0hjjtn.jpg", "Late at night"));
        return mDatas;
    }

}
