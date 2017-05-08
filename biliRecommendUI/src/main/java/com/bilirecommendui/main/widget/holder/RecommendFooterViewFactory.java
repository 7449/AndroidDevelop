package com.bilirecommendui.main.widget.holder;

import android.text.TextUtils;
import android.view.View;

import com.bilirecommendui.App;
import com.bilirecommendui.R;
import com.bilirecommendui.main.model.RecommendModel;
import com.bilirecommendui.network.Constant;

/**
 * by y on 2016/9/19
 */
public class RecommendFooterViewFactory {

    public static View createView(RecommendModel.ResultBean type) {
        switch (type.getType()) {
            case Constant.TYPE_RECOMMEND:
                return getInflate(R.layout.item_recommend_footer_recommend);
            case Constant.TYPE_BANGUMI:
                return getInflate(R.layout.item_recommend_footer_fanju);
            case Constant.TYPE_REGION:
                if (TextUtils.equals(type.getHead().getTitle(), "游戏区")) {
                    return getInflate(R.layout.item_recommend_footer_games);
                }
                return getInflate(R.layout.item_recommend_footer_refresh_more);
            default:
                return getInflate(R.layout.item_recommend_footer_refresh_more);
        }
    }


    private static View getInflate(int id) {
        return View.inflate(App.getContext(), id, null);
    }
}
