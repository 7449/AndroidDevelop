package com.lock.checkapp.widget;

import android.util.SparseBooleanArray;
import android.view.View;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;

import com.lock.R;

import java.util.ArrayList;
import java.util.List;

import framework.base.BaseRVAdapter;
import framework.base.ViewHolder;
import framework.utils.AppUtils;
import framework.utils.TextDrawableUtils;
import scan.AppModel;

/**
 * by y on 2017/2/15
 */

public class CheckAdapter extends BaseRVAdapter<AppModel> {

    private final SparseBooleanArray booleanArray = new SparseBooleanArray();
    private final List<AppModel> tempApp = new ArrayList<>();
    private boolean isShowCheckBox = false;


    public CheckAdapter(List<AppModel> mData) {
        super(mData);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_check_app;
    }

    @Override
    protected void onBind(ViewHolder holder, int position, AppModel appModel) {
        boolean isShowApp = AppUtils.isEquals(appModel.getPkgName());
        AppCompatCheckBox checkBox = holder.getAppCompatCheckBox(R.id.check_cb_check);
        AppCompatTextView textView = holder.getAppCompatTextView(R.id.check_tv_app_name);

        textView.setText(appModel.getAppLabel());
        TextDrawableUtils.INSTANCE.setAppDrawable(textView, appModel.getAppIcon());
        textView.setScaleX(isShowCheckBox ? 0.9f : 1);
        textView.setScaleY(isShowCheckBox ? 0.9f : 1);

        checkBox.setChecked(booleanArray.get(position));
        checkBox.setVisibility(isShowCheckBox && !isShowApp ? View.VISIBLE : View.GONE);

        holder.setBackgroundColor(holder.itemView, AppUtils.getColor(!isShowApp));
    }

    public boolean isShowCheckBox() {
        return isShowCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        isShowCheckBox = showCheckBox;
        notifyDataSetChanged();
    }

    public void refreshCheckBox(int position) {
        if (booleanArray.get(position)) {
            booleanArray.put(position, false);
            tempApp.remove(mData.get(position));
        } else {
            booleanArray.put(position, true);
            if (!AppUtils.isEquals(mData.get(position).getPkgName())) {
                tempApp.add(mData.get(position));
            }
        }
        notifyItemChanged(position);
    }

    public List<AppModel> getTempList() {
        return tempApp;
    }

    public void clearCheckAll() {
        tempApp.clear();
        booleanArray.clear();
    }

}
