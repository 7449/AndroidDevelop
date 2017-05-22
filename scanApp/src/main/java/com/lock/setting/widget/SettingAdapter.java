package com.lock.setting.widget;

import com.lock.R;
import com.lock.setting.model.SettingBean;

import java.util.List;

import framework.base.BaseRVAdapter;
import framework.base.ViewHolder;

/**
 * by y on 2017/2/16
 */

public class SettingAdapter extends BaseRVAdapter<SettingBean> {

    public SettingAdapter(List<SettingBean> mData) {
        super(mData);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_setting;
    }

    @Override
    protected void onBind(ViewHolder holder, int position, SettingBean settingBean) {
        holder.setTextView(R.id.tv_setting_item, settingBean.getItemString());
    }
}
