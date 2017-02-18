package com.bilirecommendui.main.widget.adapter;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.FrameLayout;

import com.bannerlayout.model.BannerModel;
import com.bilirecommendui.R;
import com.bilirecommendui.base.BaseRecyclerAdapter;
import com.bilirecommendui.base.SuperViewHolder;
import com.bilirecommendui.main.model.RecommendCompat;
import com.bilirecommendui.main.model.RecommendModel;
import com.bilirecommendui.main.widget.holder.RecommendFooterViewFactory;
import com.bilirecommendui.main.widget.holder.RecommendHeaderHolder;
import com.bilirecommendui.main.widget.holder.RecommendItemViewHolder;
import com.bilirecommendui.network.Constant;
import com.bilirecommendui.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2016/9/17.
 */
public class RecommendAdapter extends BaseRecyclerAdapter<RecommendModel> {

    private List<BannerModel> banner = null;
    private List<RecommendCompat> compats = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        return compats.get(position).getType();
    }

    @Override
    protected int getLayoutId(int viewType) {
        switch (viewType) {
            case RecommendCompat.TYPE_BANNER:
                return R.layout.item_recommend_banner;
            case RecommendCompat.TYPE_HEADER:
                return R.layout.item_recommend_header;
            case RecommendCompat.TYPE_FOOTER:
                return R.layout.item_recommend_footer;
            case RecommendCompat.TYPE_WEB_LINK:
                return R.layout.item_recommend_topic;
            case RecommendCompat.TYPE_ACTIVITY:
                return R.layout.item_recommend_activity;
            default:
                return R.layout.item_recommend_item;
        }
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        if (null == banner || isNull()) {
            return;
        }
        int newPosition = compats.get(position).getNewPosition();
        switch (compats.get(position).getType()) {
            case RecommendCompat.TYPE_BANNER:
                holder
                        .getBannerLayout(R.id.banner)
                        .initListResources(banner)
                        .initTips().
                        start(true);
                break;
            case RecommendCompat.TYPE_HEADER:
                new RecommendHeaderHolder(holder).setHeaderData(mDatas.getResult(), newPosition);
                break;
            case RecommendCompat.TYPE_FOOTER:
                FrameLayout footView = holder.getFrameLayout(R.id.fl_recommend_footer);
                footView.removeAllViews();
                footView.addView(RecommendFooterViewFactory.createView(mDatas.getResult().get(newPosition)));
                break;
            case RecommendCompat.TYPE_ITEM:
                new RecommendItemViewHolder(holder).setData(mDatas.getResult().get(newPosition).getBody().get(compats.get(position).getItemPosition()), mDatas.getResult().get(newPosition).getType());
                break;
            case RecommendCompat.TYPE_ACTIVITY:
                RecyclerView recyclerView = holder.getRecyclerView(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
                recyclerView.setAdapter(new RecommendActivityAdapter(mDatas.getResult().get(newPosition).getBody()));
                break;
            case RecommendCompat.TYPE_WEB_LINK:
                ImageLoaderUtils.display(holder.getImageView(R.id.iv_topic), mDatas.getResult().get(newPosition).getBody().get(0).getCover());
                break;
        }
    }


    @Override
    public int getItemCount() {
        int temp = 0;
        if (isNull()) {
            return temp;
        }
        compats.clear();
        compats.add(new RecommendCompat(temp += 1, RecommendCompat.TYPE_BANNER, -1));
        List<RecommendModel.ResultBean> result = mDatas.getResult();
        for (int i = 0; i < result.size(); i++) {
            switch (result.get(i).getType()) {
                case Constant.TYPE_RECOMMEND:
                case Constant.TYPE_LIVE:
                case Constant.TYPE_BANGUMI:
                case Constant.TYPE_REGION:
                    compats.add(new RecommendCompat(temp += 1, RecommendCompat.TYPE_HEADER, i));
                    for (int j = 0; j < result.get(i).getBody().size(); j++) {
                        compats.add(new RecommendCompat(temp += 1, RecommendCompat.TYPE_ITEM, i, j));
                    }
                    compats.add(new RecommendCompat(temp += 1, RecommendCompat.TYPE_FOOTER, i));
                    break;
                case Constant.TYPE_WEB_LINK:
                    compats.add(new RecommendCompat(temp += 1, RecommendCompat.TYPE_HEADER, i));
                    compats.add(new RecommendCompat(temp += 1, RecommendCompat.TYPE_WEB_LINK, i));
                    break;
                case Constant.TYPE_ACTIVITY_:
                    compats.add(new RecommendCompat(temp += 1, RecommendCompat.TYPE_HEADER, i));
                    compats.add(new RecommendCompat(temp += 1, RecommendCompat.TYPE_ACTIVITY, i));
                    break;
            }
        }
        return temp;
    }

    public void setBannerData(List<BannerModel> data) {
        this.banner = data;
    }

}
