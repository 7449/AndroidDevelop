package com.lock.showapp.widget;

import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lock.R;
import com.lock.showapp.presenter.ShowPresenter;
import com.lock.showapp.presenter.ShowPresenterImpl;
import com.lock.showapp.view.ShowView;

import java.util.ArrayList;
import java.util.List;

import framework.base.BaseFragment;
import framework.base.BaseRVAdapter;
import framework.sql.NormalBean;
import framework.sql.greendao.GreenDaoDbUtils;
import framework.utils.AppUtils;
import framework.utils.PackageUtils;
import framework.utils.UIUtils;
import scan.AppModel;

/**
 * by y on 2017/2/15
 */

public class ShowAppFragment extends BaseFragment
        implements BaseRVAdapter.OnItemClickListener<AppModel>, ShowView {

    private RecyclerView mRecyclerView;
    private AppCompatTextView mEmptyView;
    private List<NormalBean> mNormalAllData;

    public static ShowAppFragment newInstance() {
        return new ShowAppFragment();
    }

    @Override
    protected void initById() {
        mRecyclerView = getView(R.id.recyclerView);
        mEmptyView = getView(R.id.empty_view);
    }

    @Override
    protected void initData() {
        ShowPresenter presenter = new ShowPresenterImpl(this);
        mNormalAllData = GreenDaoDbUtils.getNormalAll();
        presenter.showLayout(AppUtils.isEmpty(mNormalAllData));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_show;
    }

    @Override
    public void onItemClick(int position, AppModel appInfo) {
        if (!PackageUtils.INSTANCE.startApp(getActivity(), appInfo.getPkgName())) {
            UIUtils.INSTANCE.SnackBar(getView(R.id.rootView), getString(R.string.start_app_fail));
        }
    }

    @Override
    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        ShowAppAdapter appAdapter = new ShowAppAdapter(AppUtils.copyList(mNormalAllData, new ArrayList<>()));
        mRecyclerView.setAdapter(appAdapter);
        appAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView() {
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showRecyclerView() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
