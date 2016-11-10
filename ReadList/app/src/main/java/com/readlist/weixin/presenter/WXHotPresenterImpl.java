package com.readlist.weixin.presenter;

import com.readlist.weixin.model.WXHotModel;
import com.readlist.weixin.view.WXHotView;

import framework.base.BaseModel;
import framework.base.BasePresenterImpl;
import framework.network.NetWork;

/**
 * by y on 2016/11/8
 */

public class WXHotPresenterImpl extends BasePresenterImpl<WXHotView, BaseModel<WXHotModel>> implements WXHotPresenter {


    public WXHotPresenterImpl(WXHotView view) {
        super(view);
    }

    @Override
    protected void netWorkNext(BaseModel<WXHotModel> wxHotModelBaseModel) {
        switch (wxHotModelBaseModel.getCode()) {
            case 200:
                view.setData(wxHotModelBaseModel.getNewsList());
                break;
            default:
                view.errorMessage(wxHotModelBaseModel.getMsg());
                break;
        }
    }

    @Override
    public void netWorkRequest(int page, String word) {
        isClearAdapter = page == 1;
        startNetWork(observable = NetWork.getApiService().getWXHotList(10, word, page));
    }


}
