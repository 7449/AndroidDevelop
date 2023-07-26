package com.lock.setting.widget.dialog;


import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lock.R;

import java.util.List;

import framework.base.BaseDialogFragment;
import framework.base.BaseRVAdapter;
import framework.base.ViewHolder;
import framework.sql.NormalBean;
import framework.sql.greendao.GreenDaoDbUtils;

/**
 * by y on 2017/2/16
 */

public class DeleteAppDialog extends BaseDialogFragment implements BaseRVAdapter.OnItemClickListener<NormalBean> {


    public static DeleteAppDialog newInstance() {
        return new DeleteAppDialog();
    }

    @Override
    public AlertDialog getDialog() {
        mRootView = getRootView(R.layout.dialog_delete_app);
        RecyclerView recyclerView = getView(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DeleteAdapter adapter = new DeleteAdapter(GreenDaoDbUtils.getNormalAll());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        mAlertDialog = new AlertDialog
                .Builder(getActivity())
                .setTitle(getString(R.string.dialog_delete_app_title))
                .setView(mRootView)
                .create();
        return mAlertDialog;
    }

    @Override
    protected boolean getCancelable() {
        return true;
    }

    @Override
    public void onItemClick(int position, NormalBean appInfo) {

    }

    private static class DeleteAdapter extends BaseRVAdapter<NormalBean> {

        protected DeleteAdapter(List<NormalBean> mData) {
            super(mData);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.item_delete_app;
        }

        @Override
        protected void onBind(ViewHolder holder, final int position, final NormalBean normalBean) {
            holder.setTextView(R.id.tv_delete_app, normalBean.getAppLabel());
            holder.getImageView(R.id.iv_delete).setOnClickListener(v -> {
                GreenDaoDbUtils.clearNormal(normalBean.getId());
                mData.remove(position);
                notifyDataSetChanged();
            });
        }

    }
}
