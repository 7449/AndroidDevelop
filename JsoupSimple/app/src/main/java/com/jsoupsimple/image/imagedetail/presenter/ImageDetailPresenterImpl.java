package com.jsoupsimple.image.imagedetail.presenter;


import com.jsoupsimple.image.imagedetail.view.ImageDetailView;

import java.util.List;

import framework.base.BaseModel;
import framework.base.BasePresenterImpl;
import framework.data.Constant;
import framework.network.Api;
import framework.network.NetWorkRequest;

/**
 * by y on 2016/7/28.
 */
public class ImageDetailPresenterImpl extends BasePresenterImpl<ImageDetailView, List<BaseModel>>
        implements ImageDetailPresenter {

    public ImageDetailPresenterImpl(ImageDetailView view) {
        super(view);
    }

    @Override
    protected void showProgress() {
        view.showProgress();
    }

    @Override
    protected void netWorkNext(List<BaseModel> baseModels) {
        view.setData(baseModels);
    }

    @Override
    protected void hideProgress() {
        view.hideProgress();
    }

    @Override
    protected void netWorkError() {
        view.netWorkError();
    }

    @Override
    public void netWorkRequest(String url, int page, String type) {
        NetWorkRequest.getImageDetail(getUrl(type, url, page), type, getSubscriber());
    }

    /**
     * 获取网址的url
     */
    private String getUrl(String type, String url, int page) {
        switch (type) {
            case Constant.DOU_BAN_MEI_ZI_DETAIL:
                return url;
            case Constant.M_ZI_TU_DETAIL:
                return url + Api.SLASH + page;
            default:
                return null;

        }
    }

}
