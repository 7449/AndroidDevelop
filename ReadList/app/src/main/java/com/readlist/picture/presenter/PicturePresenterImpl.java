package com.readlist.picture.presenter;

import com.readlist.picture.model.PictureModel;
import com.readlist.picture.view.PictureView;

import framework.base.BaseModel;
import framework.base.BasePresenterImpl;
import framework.network.NetWork;

/**
 * by y on 2016/11/9
 */

public class PicturePresenterImpl extends BasePresenterImpl<PictureView, BaseModel<PictureModel>> implements PicturePresenter {


    public PicturePresenterImpl(PictureView view) {
        super(view);
    }

    @Override
    protected void netWorkNext(BaseModel<PictureModel> pictureModelBaseModel) {
        switch (pictureModelBaseModel.getCode()) {
            case 200:
                view.setData(pictureModelBaseModel.getNewsList());
                break;
            default:
                view.errorMessage(pictureModelBaseModel.getMsg());
                break;
        }
    }

    @Override
    public void netWorkRequest(int page, String word) {
        isClearAdapter = page == 1;
        startNetWork(observable = NetWork.getApiService().getPictureList(10, word, page));
    }
}

