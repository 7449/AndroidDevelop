package com.readlist.weixin.widget;

import android.view.View;

import com.readlist.R;
import com.readlist.main.widget.SearchDialog;
import com.readlist.weixin.model.WXHotModel;
import com.readlist.weixin.presenter.WXHotPresenterImpl;
import com.readlist.weixin.view.WXHotView;

import java.util.ArrayList;
import java.util.List;

import framework.base.BaseModel;
import framework.base.BaseRecyclerViewAdapter;
import framework.base.RefreshFragment;
import framework.data.Constant;
import framework.utils.UIUtils;

/**
 * by y on 2016/11/8
 */

public class WXHotFragment extends RefreshFragment<BaseModel<WXHotModel>, WXHotAdapter, WXHotPresenterImpl>
        implements WXHotView,
        View.OnClickListener,
        SearchDialog.SearchDialogInterface,
        BaseRecyclerViewAdapter.OnItemClickListener<WXHotModel>,
        BaseRecyclerViewAdapter.OnItemLongClickListener<WXHotModel> {

    private String word = null;

    public static WXHotFragment startWxHot() {
        return new WXHotFragment();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fa_btn:
                if (getActivity() != null) {
                    SearchDialog.showDialog(getActivity(), this);
                }
                break;
        }
    }


    @Override
    protected void initCreate() {
        getActivity().findViewById(R.id.fa_btn).setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLongClickListener(this);
    }

    @Override
    protected WXHotAdapter initAdapter() {
        return new WXHotAdapter(new ArrayList<WXHotModel>());
    }

    @Override
    protected WXHotPresenterImpl initPresenter() {
        return new WXHotPresenterImpl(this);
    }

    @Override
    protected int initRecyclerManagerCount() {
        return 1;
    }

    @Override
    protected void netWork() {
        mPresenter.netWorkRequest(page, word);
    }


    @Override
    public void enterWord(String word) {
        this.word = word;
        page = 1;
        netWork();
    }

    @Override
    public void setData(List<WXHotModel> newslistBeen) {
        mAdapter.addAll(newslistBeen);
    }


    @Override
    public void onItemClick(View view, int position, WXHotModel info) {
        UIUtils.startBrowser(getActivity(), info.getUrl());
    }

    @Override
    public void onLongClick(View view, int position, WXHotModel info) {
        UIUtils.share(getActivity(), Constant.WX_HOT_SHARE_TITLE + info.getUrl());
    }

    @Override
    public void wordEmpty() {
        if (getActivity() != null) {
            UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), Constant.WORD_NULL);
        } else {
            UIUtils.Toast(Constant.WORD_NULL);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        word = null;
    }
}
