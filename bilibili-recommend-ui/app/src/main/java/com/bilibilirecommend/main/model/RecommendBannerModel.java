package com.bilibilirecommend.main.model;

import com.bannerlayout.model.BannerModel;

import java.util.List;

/**
 * by y on 2016/9/17.
 */
public class RecommendBannerModel {


    private int code;

    private List<BannerModel> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<BannerModel> getData() {
        return data;
    }

    public void setData(List<BannerModel> data) {
        this.data = data;
    }

}
