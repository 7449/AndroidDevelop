package com.bilirecommendui.main.widget.holder;

import android.view.View;

import com.bilirecommendui.App;
import com.bilirecommendui.R;

/**
 * by y on 2016/9/19
 */
public class RecommendFooterViewFactory {

    public static View createView(int id) {
        View view = null;
        switch (id) {
            case 0:
                view = getInflate(R.layout.item_recommend_footer_recommend);
                break;
            case 1:
                view = getInflate(R.layout.item_recommend_footer_fanju);
                break;
            case 2:
                view = getInflate(R.layout.item_recommend_footer_refresh_more);
                break;
            case 3:
                view = getInflate(R.layout.item_recommend_footer_games);
                break;
        }
        return view;
    }


    private static View getInflate(int id) {
        return View.inflate(App.getContext(), id, null);
    }
}
