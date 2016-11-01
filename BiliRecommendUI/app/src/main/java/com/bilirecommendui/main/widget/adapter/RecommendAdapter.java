package com.bilirecommendui.main.widget.adapter;


import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bannerlayout.model.BannerModel;
import com.bilirecommendui.R;
import com.bilirecommendui.base.BaseRecyclerAdapter;
import com.bilirecommendui.base.SuperViewHolder;
import com.bilirecommendui.main.model.RecommendModel;
import com.bilirecommendui.main.widget.holder.RecommendActivityHolder;
import com.bilirecommendui.main.widget.holder.RecommendBannerHolder;
import com.bilirecommendui.main.widget.holder.RecommendFooterHolder;
import com.bilirecommendui.main.widget.holder.RecommendHeaderHolder;
import com.bilirecommendui.main.widget.holder.RecommendTopicHolder;
import com.bilirecommendui.network.ImageLoaderUtils;
import com.bilirecommendui.utils.SpannableUtils;

import java.util.List;

import static com.bilirecommendui.network.Constant.TYPE_ACTIVITY_;
import static com.bilirecommendui.network.Constant.TYPE_BANGUMI;
import static com.bilirecommendui.network.Constant.TYPE_FOOTER_;
import static com.bilirecommendui.network.Constant.TYPE_HEADER_;
import static com.bilirecommendui.network.Constant.TYPE_LIVE;
import static com.bilirecommendui.network.Constant.TYPE_RECOMMEND;
import static com.bilirecommendui.network.Constant.TYPE_REGION;
import static com.bilirecommendui.network.Constant.TYPE_WEB_LINK;

/**
 * by y on 2016/9/17.
 */
public class RecommendAdapter extends BaseRecyclerAdapter<RecommendModel> {

    private static final int TYPE_VIEWPAGER = 0;
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 3;
    private static final int TYPE_FOOTER = 4;
    private static final int TYPE_TOPIC = 5;
    private static final int TYPE_ACTIVITY = 7;

    private List<BannerModel> banner = null;

    @Override
    public int getItemViewType(int position) {
        if (isBanner(position)) {
            return TYPE_VIEWPAGER;
        }
        if (null != mDatas.getMaps().get(position) && !TextUtils.isEmpty(mDatas.getMaps().get(position).getType())) {
            switch (mDatas.getMaps().get(position).getType()) {
                case TYPE_HEADER_:
                    return TYPE_HEADER;
                case TYPE_FOOTER_:
                    return TYPE_FOOTER;
                case TYPE_RECOMMEND:
                case TYPE_LIVE:
                case TYPE_BANGUMI:
                case TYPE_REGION:
                    return TYPE_ITEM;
                case TYPE_ACTIVITY_:
                    return TYPE_ACTIVITY;
                case TYPE_WEB_LINK:
                    return TYPE_TOPIC;
            }
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_VIEWPAGER:
                return new RecommendBannerHolder(getView(parent, R.layout.item_recommend_banner));
            case TYPE_HEADER:
                return new RecommendHeaderHolder(getView(parent, R.layout.item_recommend_header));
            case TYPE_FOOTER:
                return new RecommendFooterHolder(getView(parent, R.layout.item_recommend_footer));
            case TYPE_TOPIC:
                return new RecommendTopicHolder(getView(parent, R.layout.item_recommend_topic));
            case TYPE_ACTIVITY:
                return new RecommendActivityHolder(getView(parent, R.layout.item_recommend_activity));
            default:
                return new RecommendItemViewHolder(getView(parent, R.layout.item_recommend_item));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (null == banner || isNull()) {
            return;
        }
        if (holder instanceof RecommendBannerHolder) {
            ((RecommendBannerHolder) holder).setBanner(banner);
        }

        if (null != mDatas.getMaps().get(position) && !TextUtils.isEmpty(mDatas.getMaps().get(position).getType())) {
            int newPosition = mDatas.getMaps().get(position).getPosition();
            int itemPosition = mDatas.getMaps().get(position).getItemPosition();
            switch (mDatas.getMaps().get(position).getType()) {
                case TYPE_HEADER_:
                    if (holder instanceof RecommendHeaderHolder) {
                        ((RecommendHeaderHolder) holder).setHeaderData(mDatas.getResult().get(newPosition).getHead(), mDatas.getResult().get(newPosition).getHead().getTitle(), mDatas.getMaps().get(position).getImagePage());
                    }
                    break;
                case TYPE_FOOTER_:
                    if (holder instanceof RecommendFooterHolder) {
                        ((RecommendFooterHolder) holder).setFooterData(mDatas.getResult().get(newPosition).getHead().getTitle());
                    }
                    break;
                case TYPE_ACTIVITY_:
                    if (holder instanceof RecommendActivityHolder) {
                        ((RecommendActivityHolder) holder).setData(mDatas.getResult().get(newPosition).getBody());
                    }
                    break;
                case TYPE_WEB_LINK:
                    if (holder instanceof RecommendTopicHolder) {
                        ((RecommendTopicHolder) holder).setTopicData(mDatas.getResult().get(newPosition).getBody());
                    }
                    break;
                default:
                    if (holder instanceof RecommendItemViewHolder) {
                        ((RecommendItemViewHolder) holder).setData(
                                mDatas.getResult().get(newPosition).getBody().get(itemPosition),
                                mDatas.getResult().get(newPosition).getType());
                    }
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        int temp = 0;
        if (isNull()) {
            return temp;
        }
        return mDatas.getItemPositionCount() + 1;
    }

    public void setBannerData(List<BannerModel> data) {
        this.banner = data;
    }


    public class RecommendItemViewHolder extends SuperViewHolder {
        private final TextView mTitle;
        private final ImageView mTitlePage;

        private final RelativeLayout mHot;
        private final TextView mHotName;
        private final TextView mHotBarrageCount;

        private final RelativeLayout mFanju;
        private final TextView mFanjuNum;
        private final TextView mFanjuTime;

        private final RelativeLayout mLive;
        private final TextView mLiveName;
        private final TextView mLiveWatchNum;

        public RecommendItemViewHolder(View itemView) {
            super(itemView);
            mHot = get(R.id.item_hot);
            mHotName = get(R.id.tv_hot_name);
            mHotBarrageCount = get(R.id.tv_hot_barrage_count);

            mFanju = get(R.id.item_fanju);
            mFanjuNum = get(R.id.tv_fanju_name);
            mFanjuTime = get(R.id.tv__fanju_time);

            mLive = get(R.id.item_live);
            mLiveName = get(R.id.tv_live_name);
            mLiveWatchNum = get(R.id.tv_live_watch_num);

            mTitle = get(R.id.tv_title);
            mTitlePage = get(R.id.iv_title_page);
        }


        @SuppressLint("SetTextI18n")
        public void setData(final RecommendModel.ResultBean.BodyBean bodyBean, final String type) {
            switch (type) {
                case TYPE_LIVE:
                    mHot.setVisibility(View.GONE);
                    mFanju.setVisibility(View.GONE);
                    mLive.setVisibility(View.VISIBLE);

                    mTitle.setText(SpannableUtils.getHomeTitlePageType(bodyBean.getArea(), bodyBean.getTitle()));
                    mLiveName.setText(bodyBean.getUp());
                    mLiveWatchNum.setText(String.valueOf(bodyBean.getOnline()));
                    break;
                case TYPE_BANGUMI:
                    mHot.setVisibility(View.GONE);
                    mLive.setVisibility(View.GONE);
                    mFanju.setVisibility(View.VISIBLE);

                    mTitle.setText(bodyBean.getTitle());
                    mFanjuNum.setText(bodyBean.getDesc1());
                    mFanjuTime.setText(String.valueOf(bodyBean.getStatus()));
                    break;
                default:
                    mFanju.setVisibility(View.GONE);
                    mLive.setVisibility(View.GONE);
                    mHot.setVisibility(View.VISIBLE);

                    mHotName.setText(bodyBean.getPlay());
                    mHotBarrageCount.setText("弹幕数：" + bodyBean.getDanmaku());
                    mTitle.setText(bodyBean.getTitle());
                    break;
            }
            ImageLoaderUtils.display(mTitlePage, bodyBean.getCover());
        }
    }
}
