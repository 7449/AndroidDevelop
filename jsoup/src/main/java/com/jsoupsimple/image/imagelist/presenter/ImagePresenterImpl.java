package com.jsoupsimple.image.imagelist.presenter;


import com.jsoupsimple.R;
import com.jsoupsimple.image.imagelist.view.ImageView;

import java.util.List;

import framework.base.BaseModel;
import framework.base.BasePresenterImpl;
import framework.data.Constant;
import framework.network.Api;
import framework.network.NetWorkRequest;
import framework.utils.UIUtils;

/**
 * by y on 2016/7/28.
 */
public class ImagePresenterImpl extends BasePresenterImpl<ImageView, List<BaseModel>>
        implements ImagePresenter {


    public ImagePresenterImpl(ImageView view) {
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
    public void netWorkRequest(int id, final int page, String type) {
        if (page == 1) {
            view.removeAdapter();
        }
        NetWorkRequest.getImageList(getUrl(id, page, type), type, getSubscriber());
    }

    /**
     * 获取图片的url
     */
    private String getUrl(int id, int page, String type) {
        switch (type) {
            case Constant.DOU_BAN_MEI_ZI:
                if (id == 8) {
                    return Api.VIDEO + page;
                }
                return Api.DBMZ_API + id + Api.dou_ban_mei_zi_link + page;
            default:
                return Api.M_ZI_TU_API + UIUtils.getStringArray(R.array.mzitu_array_suffix)[id] + Api.SLASH + Api.PAGE + Api.SLASH + page;
        }
    }

}
