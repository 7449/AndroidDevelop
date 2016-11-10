package com.readlist.weixin.presenter;

import com.readlist.weixin.model.WXHotModel;
import com.readlist.weixin.view.WXHotView;

import framework.base.BasePresenterImpl;
import framework.network.NetWork;

/**
 * by y on 2016/11/8
 */

public class WXHotPresenterImpl extends BasePresenterImpl<WXHotView, WXHotModel> implements WXHotPresenter {


    public WXHotPresenterImpl(WXHotView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int page, String word) {
        isClearAdapter = page == 1;
        startNetWork(observable = NetWork.getApiService().getWXHotList(10, word, page));
    }

    @Override
    protected void netWorkNext(WXHotModel wxHotModel) {
        switch (wxHotModel.getCode()) {
            case 200:
                view.setData(wxHotModel.getNewslist());
                break;
            default:
                view.errorMessage(wxHotModel.getMsg());
                break;
        }
    }

}
