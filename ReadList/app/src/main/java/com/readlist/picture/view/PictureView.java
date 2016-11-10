package com.readlist.picture.view;

import com.readlist.picture.model.PictureModel;

import java.util.List;

import framework.base.BaseView;

/**
 * by y on 2016/11/9
 */

public interface PictureView extends BaseView<PictureModel> {
    void setData(List<PictureModel.NewslistBean> newslistBeen);
}
