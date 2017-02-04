package framework.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.readlist.R;

import framework.data.Constant;
import framework.utils.UIUtils;
import framework.widget.ReadRecyclerView;
import framework.widget.ReadSwipeRefreshLayout;
import rx.Observable;

/**
 * by y on 2016/11/9
 */

public abstract class RefreshFragment<MODEL,
        ADAPTER extends BaseRecyclerViewAdapter,
        PRESENTER extends BasePresenterImpl> extends BaseFragment
        implements BaseView<MODEL>,
        ReadRecyclerView.LoadingListener,
        SwipeRefreshLayout.OnRefreshListener {

    public ReadRecyclerView mRecyclerView;
    public ReadSwipeRefreshLayout mRefresh;
    public ImageView mEmptyView;
    public ADAPTER mAdapter;
    public PRESENTER mPresenter;
    public int page = 1;

    @Override
    protected void toolbarOnclick() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    protected void initById() {
        mRecyclerView = getView(R.id.recyclerView);
        mRefresh = getView(R.id.srf_layout);
        mEmptyView = getView(R.id.empty_view);
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netWork();
            }
        });
    }


    @Override
    protected void initBundle(Bundle bundle) {

    }

    @Override
    protected void initData() {
        mAdapter = initAdapter();
        mPresenter = initPresenter();
        mRecyclerView.init(initRecyclerManagerCount(), StaggeredGridLayoutManager.VERTICAL, mAdapter, this, mEmptyView);
        mRefresh.init(this);
        initCreate();
    }

    @Override
    public void onRefresh() {
        page = 1;
        netWork();
    }

    @Override
    public void onLoadMore() {
        ++page;
        netWork();
    }

    protected abstract void initCreate();

    protected abstract ADAPTER initAdapter();

    protected abstract PRESENTER initPresenter();

    protected abstract int initRecyclerManagerCount();

    protected abstract void netWork();

    @Override
    public void clearAdapter() {
        mAdapter.removeAll();
    }


    @Override
    public void netWorkError() {
        if (getActivity() != null) {
            UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), Constant.NETWORK_ERROR);
        } else {
            UIUtils.Toast(Constant.NETWORK_ERROR);
        }
    }

    @Override
    public void showProgress() {
        mRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mRefresh.setRefreshing(false);
    }

    @Override
    public void viewBindToLifecycle(Observable<MODEL> observable) {
        if (observable != null) {
            observable.compose(this.<MODEL>bindToLifecycle());
        }
    }

    @Override
    public void errorMessage(String msg) {
        if (getActivity() != null) {
            UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), msg);
        } else {
            UIUtils.Toast(msg);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mRefresh != null) {
            mRefresh.setRefreshing(false);
        }
    }
}