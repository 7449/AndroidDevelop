package com.jsoupsimple.image.imagelist.view;

import java.util.List;

import framework.base.BaseModel;
import framework.base.BaseView;

/**
 * by y on 2016/7/28.
 */
public interface ImageView extends BaseView {

    void setData(List<BaseModel> datas);

    void removeAdapter();
}
