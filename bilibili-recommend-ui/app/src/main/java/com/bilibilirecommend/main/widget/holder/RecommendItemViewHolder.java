package com.bilibilirecommend.main.widget.holder;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibilirecommend.R;
import com.bilibilirecommend.base.SuperViewHolder;
import com.bilibilirecommend.main.model.RecommendModel;
import com.bilibilirecommend.network.Constant;
import com.bilibilirecommend.utils.CompoundDrawableUtils;
import com.bilibilirecommend.utils.ImageLoaderUtils;
import com.bilibilirecommend.utils.SpannableUtils;


/**
 * by y on 2017/2/19.
 */

public class RecommendItemViewHolder {
    private final TextView mTitle;
    private final ImageView mTitlePage;

    private final TextView mLiveName;
    private final TextView mLiveWatchNum;

    public RecommendItemViewHolder(SuperViewHolder superViewHolder) {

        mLiveName = superViewHolder.get(R.id.tv_live_name);
        mLiveWatchNum = superViewHolder.get(R.id.tv_live_watch_num);

        mTitle = superViewHolder.get(R.id.tv_title);
        mTitlePage = superViewHolder.get(R.id.iv_title_page);
    }


    @SuppressLint("SetTextI18n")
    public void setData(final RecommendModel.ResultBean.BodyBean bodyBean, final String type) {
        switch (type) {
            case Constant.TYPE_LIVE:
                CompoundDrawableUtils.setItem(mLiveWatchNum, R.drawable.ic_watching);
                mTitle.setText(SpannableUtils.getHomeTitlePageType(bodyBean.getArea(), bodyBean.getTitle()));
                mLiveName.setText(bodyBean.getUp());
                mLiveWatchNum.setText(String.valueOf(bodyBean.getOnline()));
                break;
            case Constant.TYPE_BANGUMI:
                mLiveWatchNum.setCompoundDrawables(null, null, null, null);
                mTitle.setText(bodyBean.getTitle());
                mLiveName.setText(bodyBean.getDesc1());
                mLiveWatchNum.setText(String.valueOf(bodyBean.getStatus()));
                break;
            default:
                mLiveWatchNum.setCompoundDrawables(null, null, null, null);
                mLiveName.setText(bodyBean.getPlay());
                mLiveWatchNum.setText("弹幕数：" + bodyBean.getDanmaku());
                mTitle.setText(bodyBean.getTitle());
                break;
        }
        ImageLoaderUtils.display(mTitlePage, bodyBean.getCover());
    }
}
