package com.bilirecommendui.main.widget.holder;


import android.view.View;
import android.widget.FrameLayout;

import com.bilirecommendui.R;
import com.bilirecommendui.base.SuperViewHolder;

import static com.bilirecommendui.network.Constant.TYPE_CURRENT_FOCUS;
import static com.bilirecommendui.network.Constant.TYPE_GAMES;
import static com.bilirecommendui.network.Constant.TYPE_RECOMMEND_IMAGE;

/**
 * by y on 2016/9/17.
 */
public class RecommendFooterHolder extends SuperViewHolder {

    private FrameLayout mView;

    public RecommendFooterHolder(View itemView) {
        super(itemView);
        mView = get(R.id.fl_recommend_footer);
    }

    public void setFooterData(String title) {
        if (null != mView) {
            mView.removeAllViews();
            switch (title) {
                case TYPE_CURRENT_FOCUS:
                    mView.addView(RecommendFooterViewFactory.createView(0));
                    break;
                case TYPE_RECOMMEND_IMAGE:
                    mView.addView(RecommendFooterViewFactory.createView(1));
                    break;
                case TYPE_GAMES:
                    mView.addView(RecommendFooterViewFactory.createView(3));
                    break;
                default:
                    mView.addView(RecommendFooterViewFactory.createView(2));
                    break;
            }
        }
    }

}
