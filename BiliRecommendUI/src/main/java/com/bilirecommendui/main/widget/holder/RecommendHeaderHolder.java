package com.bilirecommendui.main.widget.holder;


import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.TextView;

import com.bilirecommendui.App;
import com.bilirecommendui.R;
import com.bilirecommendui.base.SuperViewHolder;
import com.bilirecommendui.main.model.RecommendModel;
import com.bilirecommendui.network.Constant;
import com.bilirecommendui.utils.CompoundDrawableUtils;
import com.bilirecommendui.utils.SpannableUtils;

import java.util.List;


/**
 * by y on 2016/9/17.
 */
public class RecommendHeaderHolder {


    private TextView mTitle;
    private TextView mTv;

    public RecommendHeaderHolder(SuperViewHolder superViewHolder) {
        mTitle = superViewHolder.get(R.id.tv_title);
        mTv = superViewHolder.get(R.id.tv);
    }

    public void setHeaderData(List<RecommendModel.ResultBean> head, int newPosition) {
        RecommendModel.ResultBean.HeadBean headBean = head.get(newPosition).getHead();
        mTitle.setText(headBean.getTitle());
        switch (head.get(newPosition).getType()) {
            case Constant.TYPE_RECOMMEND:
                setRecommendTv();
                setTitleImage(R.drawable.ic_header_hot);
                break;
            case Constant.TYPE_LIVE:
                setTitleImage(R.drawable.ic_head_live);
                setLiveTv(headBean.getCount());
                break;
            case Constant.TYPE_BANGUMI:
                CompoundDrawableUtils.setTitle(mTitle, getRegionImage(headBean.getParam()));
                setBangumi();
                break;
            case Constant.TYPE_WEB_LINK:
                setTitleImage(R.drawable.ic_header_topic);
                mTitle.setText("话题");
                setDefault();
                break;
            default:
                CompoundDrawableUtils.setTitle(mTitle, getRegionImage(headBean.getParam()));
                setDefault();
                break;
        }
    }

    private void setTitleImage(int id) {
        CompoundDrawableUtils.setTitle(mTitle, id);
    }

    private int getRegionImage(String param) {
        switch (param) {
            case "1":
                return R.drawable.ic_category_t1;
            case "3":
                return R.drawable.ic_category_t3;
            case "129":
                return R.drawable.ic_category_t129;
            case "4":
                return R.drawable.ic_category_t4;
            case "119":
                return R.drawable.ic_category_t119;
            case "160":
                return R.drawable.ic_category_t160;
            case "36":
                return R.drawable.ic_category_t36;
            case "155":
                return R.drawable.ic_category_t155;
            case "5":
                return R.drawable.ic_category_t5;
            case "11":
                return R.drawable.ic_category_t11;
            case "23":
                return R.drawable.ic_category_t23;
            case "13":
                return R.drawable.ic_category_t13;
            case "subarea":
                return R.drawable.ic_header_activity_center;
            default:
                return R.drawable.ic_category_t1;
        }
    }

    private void setRecommendTv() {
        mTv.clearComposingText();
        mTv.setTextColor(ContextCompat.getColor(App.getContext(), R.color.white));
        mTv.setBackgroundColor(ContextCompat.getColor(App.getContext(), R.color.yellow));
        mTv.setCompoundDrawablePadding(5);
        mTv.setGravity(Gravity.CENTER);
        mTv.setText(R.string.home_recommend_header_ranking);
        CompoundDrawableUtils.setRecommend(mTv, R.drawable.ic_header_indicator_rank);
    }

    private void setBangumi() {
        mTv.setBackgroundColor(ContextCompat.getColor(App.getContext(), R.color.gray));
        mTv.setText(App.getContext().getString(R.string.home_recommend_header_look_more));
    }

    private void setDefault() {
        mTv.setCompoundDrawables(null, null, null, null);
        mTv.setTextColor(ContextCompat.getColor(App.getContext(), R.color.white));
        mTv.setBackgroundColor(ContextCompat.getColor(App.getContext(), R.color.gray));
        mTv.setText(App.getContext().getString(R.string.home_recommend_header_look));
    }

    private void setLiveTv(Integer count) {
        mTv.setBackgroundDrawable(null);
        mTv.setTextColor(ContextCompat.getColor(App.getContext(), R.color.black));
        mTv.setCompoundDrawablePadding(8);
        CompoundDrawableUtils.setLive(mTv, R.drawable.ic_gray_arrow_right);
        mTv.setText(SpannableUtils.getHomeCountText(count));
    }

}