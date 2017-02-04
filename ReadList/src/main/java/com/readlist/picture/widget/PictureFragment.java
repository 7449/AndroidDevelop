package com.readlist.picture.widget;

import android.view.View;

import com.readlist.R;
import com.readlist.main.widget.SearchDialog;
import com.readlist.picture.model.PictureModel;
import com.readlist.picture.presenter.PicturePresenterImpl;
import com.readlist.picture.view.PictureView;

import java.util.ArrayList;
import java.util.List;

import framework.base.BaseModel;
import framework.base.BaseRecyclerViewAdapter;
import framework.base.RefreshFragment;
import framework.data.Constant;
import framework.utils.UIUtils;

/**
 * by y on 2016/11/9
 */

public class PictureFragment extends RefreshFragment<BaseModel<PictureModel>, PictureAdapter, PicturePresenterImpl>
        implements PictureView,
        View.OnClickListener,
        SearchDialog.SearchDialogInterface,
        BaseRecyclerViewAdapter.OnItemClickListener<PictureModel> {

    private String word = null;

    public static PictureFragment startWxHot() {
        return new PictureFragment();
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
    }

    @Override
    protected PictureAdapter initAdapter() {
        return new PictureAdapter(new ArrayList<PictureModel>());
    }

    @Override
    protected PicturePresenterImpl initPresenter() {
        return new PicturePresenterImpl(this);
    }

    @Override
    protected int initRecyclerManagerCount() {
        return 2;
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
    public void wordEmpty() {
        if (getActivity() != null) {
            UIUtils.SnackBar(getActivity().findViewById(R.id.coordinatorLayout), Constant.WORD_NULL);
        } else {
            UIUtils.Toast(Constant.WORD_NULL);
        }
    }

    @Override
    public void setData(List<PictureModel> newslistBeen) {
        mAdapter.addAll(newslistBeen);
    }


    @Override
    public void onItemClick(View view, int position, PictureModel info) {
        UIUtils.startBrowser(getActivity(), info.getUrl());
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