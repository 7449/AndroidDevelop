package com.bilirecommendui.main.widget.holder;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bilirecommendui.R;
import com.bilirecommendui.base.SuperViewHolder;
import com.bilirecommendui.main.model.RecommendModel;
import com.bilirecommendui.network.CompoundDrawableUtils;
import com.bilirecommendui.utils.SpannableUtils;

import static com.bilirecommendui.network.Constant.TYPE_CURRENT_FOCUS;
import static com.bilirecommendui.network.Constant.TYPE_HOT_AIR;
import static com.bilirecommendui.network.Constant.TYPE_RECOMMEND_IMAGE;

/**
 * by y on 2016/9/17.
 */
public class RecommendHeaderHolder extends SuperViewHolder {


    private TextView mTitle;
    private TextView mCount;
    private TextView mTitleIcon;
    private TextView mLook;

    public RecommendHeaderHolder(View itemView) {
        super(itemView);
        mTitle = get(R.id.tv_title);
        mCount = get(R.id.tv_count);
        mTitleIcon = get(R.id.tv_title_icon);
        mLook = get(R.id.tv_look);
    }

    @SuppressLint("SetTextI18n")
    public void setHeaderData(RecommendModel.ResultBean.HeadBean head, String type, int imagePage) {
        if (imagePage != -1) {
            CompoundDrawableUtils.setSrc(mTitle, imagePage);
        }
        if (TextUtils.isEmpty(head.getTitle())) {
            mTitle.setText("话题");
            mLook.setVisibility(View.GONE);
            mCount.setVisibility(View.GONE);
            mTitleIcon.setVisibility(View.GONE);
            return;
        }
        mTitle.setText(head.getTitle());
        switch (type) {
            case TYPE_CURRENT_FOCUS:
                mLook.setVisibility(View.GONE);
                mCount.setVisibility(View.GONE);
                mTitleIcon.setVisibility(View.VISIBLE);
                break;
            case TYPE_HOT_AIR:
                mLook.setVisibility(View.GONE);
                mCount.setVisibility(View.VISIBLE);
                mTitleIcon.setVisibility(View.GONE);
                mCount.setText(SpannableUtils.getHomeCountText(head.getCount()));
                break;
            case TYPE_RECOMMEND_IMAGE:
                mLook.setText(itemView.getContext().getString(R.string.home_recommend_header_look_more));
                mLook.setVisibility(View.VISIBLE);
                mCount.setVisibility(View.GONE);
                mTitleIcon.setVisibility(View.GONE);
                break;
            default:
                mLook.setText(itemView.getContext().getString(R.string.home_recommend_header_look));
                mLook.setVisibility(View.VISIBLE);
                mCount.setVisibility(View.GONE);
                mTitleIcon.setVisibility(View.GONE);
                break;
        }
    }

}