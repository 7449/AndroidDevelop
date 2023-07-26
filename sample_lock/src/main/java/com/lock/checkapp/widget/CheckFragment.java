package com.lock.checkapp.widget;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lock.R;
import com.lock.checkapp.presenter.CheckPresenter;
import com.lock.checkapp.presenter.CheckPresenterImpl;
import com.lock.checkapp.view.CheckView;

import java.util.ArrayList;
import java.util.List;

import framework.base.BaseFragment;
import framework.base.BaseRVAdapter;
import framework.sql.greendao.GreenDaoDbUtils;
import framework.utils.AppUtils;
import framework.utils.PackageUtils;
import framework.utils.UIUtils;
import scan.AppModel;

/**
 * by y on 2017/2/15
 */

public class CheckFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        BaseRVAdapter.OnItemClickListener<AppModel>,
        BaseRVAdapter.OnLongClickListener<AppModel>,
        View.OnKeyListener,
        View.OnClickListener, CheckView {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private CheckAdapter mAdapter;
    private FloatingActionButton mSaveApp;
    private FloatingActionButton mSaveAppAll;
    private CheckPresenter mPresenter;

    public static CheckFragment newInstance() {
        return new CheckFragment();
    }

    @Override
    protected void initById() {
        mSwipeRefreshLayout = getView(R.id.srf_view);
        mRecyclerView = getView(R.id.recyclerView);
        mSaveApp = getView(R.id.check_save_app);
        mSaveAppAll = getView(R.id.check_save_app_all);
    }

    @Override
    protected void initData() {
        mPresenter = new CheckPresenterImpl(this);
        mSaveApp.setOnClickListener(this);
        mSaveAppAll.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAdapter = new CheckAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLongClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        onRefresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_check;
    }


    @Override
    public void onResume() {
        super.onResume();
        View view = getView();
        if (view != null) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(this);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.onScanApp();
    }

    @Override
    public void onItemClick(int position, AppModel appInfo) {
        if (mAdapter.isShowCheckBox()) {
            mAdapter.refreshCheckBox(position);
        } else {
            if (!PackageUtils.INSTANCE.startApp(getActivity(), appInfo.getPkgName())) {
                UIUtils.INSTANCE.SnackBar(getView(R.id.rootView), getString(R.string.start_app_fail));
            }
        }
    }

    @Override
    public void onLongClick(int position, AppModel appInfo) {
        if (mAdapter.isShowCheckBox()) {
            clearAdapter();
        } else {
            mAdapter.setShowCheckBox(true);
            mAdapter.refreshCheckBox(position);
        }
    }

    @Override
    public void showProgressBar() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onScanSuccess(List<AppModel> data) {
        mAdapter.clearAll();
        mAdapter.addAll(AppUtils.getList(data));
    }

    @Override
    public void onScanError(Throwable e) {
        UIUtils.INSTANCE.SnackBar(getView(R.id.rootView), getString(R.string.scan_app_fail));
    }

    @Override
    public void removeAdapter() {
        clearAdapter();
    }

    @Override
    public Context getScanContext() {
        return getActivity();
    }

    @Override
    public void clearDb() {
        GreenDaoDbUtils.clearNormal();
    }

    @Override
    public void insertDB() {
        AppUtils.insertDB(mAdapter.getTempList());
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK && mAdapter.isShowCheckBox()) {
            removeAdapter();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        List<AppModel> tempList = mAdapter.getTempList();
        if (!AppUtils.isEmpty(tempList)) {
            mPresenter.onClick(v.getId());
            clearAdapter();
            UIUtils.INSTANCE.SnackBar(getView(R.id.rootView), getString(R.string.check_app_save_success));
        }
    }

    private void clearAdapter() {
        mAdapter.clearCheckAll();
        mAdapter.setShowCheckBox(false);
    }

}
