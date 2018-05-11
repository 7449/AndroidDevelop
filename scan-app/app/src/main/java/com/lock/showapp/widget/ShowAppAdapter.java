package com.lock.showapp.widget;

import android.support.v7.widget.AppCompatTextView;

import com.lock.R;

import java.util.List;

import framework.base.BaseRVAdapter;
import framework.base.ViewHolder;
import framework.utils.TextDrawableUtils;
import scan.AppModel;

/**
 * by y on 2017/2/15
 */

public class ShowAppAdapter extends BaseRVAdapter<AppModel> {

    public ShowAppAdapter(List<AppModel> mData) {
        super(mData);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_show;
    }

    @Override
    protected void onBind(ViewHolder holder, int position, AppModel appModel) {
        if (appModel.getAppIcon() != null && appModel.getAppLabel() != null) {
            AppCompatTextView textView = holder.getAppCompatTextView(R.id.show_tv_app_name);
            textView.setText(appModel.getAppLabel());
            TextDrawableUtils.setAppDrawable(textView, appModel.getAppIcon());
        }
    }

}
