package com.tabfragment;

import com.bannerlayout.Interface.BannerModelCallBack;

/**
 * by y on 29/06/2017.
 */

public class BannerModel implements BannerModelCallBack<String> {


    private String url;
    private String title;

    public BannerModel(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public BannerModel(String url) {
        this.url = url;
    }

    @Override
    public String getBannerUrl() {
        return url;
    }

    @Override
    public String getBannerTitle() {
        return null;
    }
}
