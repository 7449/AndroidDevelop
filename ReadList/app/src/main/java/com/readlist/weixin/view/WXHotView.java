package com.readlist.weixin.view;

import com.readlist.weixin.model.WXHotModel;

import java.util.List;

import framework.base.BaseModel;
import framework.base.BaseView;

/**
 * by y on 2016/11/8
 */

public interface WXHotView extends BaseView<BaseModel<WXHotModel>> {

    void setData(List<WXHotModel> newslistBeen);
}
