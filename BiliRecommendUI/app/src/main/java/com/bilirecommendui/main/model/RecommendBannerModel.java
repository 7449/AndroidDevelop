package com.bilirecommendui.main.model;

import com.bannerlayout.model.BannerModel;

import java.util.List;

/**
 * by y on 2016/9/17.
 */
public class RecommendBannerModel {

    /**
     * code : 0
     * data : [{"title":"这就是传说中那场蓄谋已久的复活赛了","value":"http://www.bilibili.com/video/av6315895/","image":"http://i0.hdslb.com/bfs/archive/c45d4061435a6bde08f16bbcc9df4d86099defc4.jpg","type":2,"weight":2,"remark":"","hash":"b624927a9866acd49013334209ccfff8"},{"title":"FGO","value":"61","image":"http://i0.hdslb.com/bfs/archive/732c4ffcbfbd233e68f43c8c0a3e1e9ab1454ab6.jpg","type":1,"weight":2,"remark":"","hash":"c90972c41168e38b2013c1295f09ca96"},{"title":"逗趣问答一战成神","value":"http://acg.tv/u1ue","image":"http://i0.hdslb.com/bfs/archive/056f5ad71f17a9fbb9581f16735768e3d9c54008.png","type":2,"weight":3,"remark":"","hash":"3e054fa6f2fb4632eb8c7a6203e5201c"},{"title":"动画区频道精选 No.29","value":"http://www.bilibili.com/topic/v2/phone1515.html","image":"http://i0.hdslb.com/bfs/archive/0d42fbdeedee06c00743aa1fe8572a8497d79f4d.jpg","type":2,"weight":3,"remark":"","hash":"d8b6e60f365c6e43caaf600294efdced"},{"title":"跟好这趟TGS列车！","value":"http://www.bilibili.com/html/activity-TGS2016-m.html","image":"http://i0.hdslb.com/bfs/archive/1564b1f29c76df0af3092684a26ba60a96189fe3.jpg","type":2,"weight":3,"remark":"","hash":"feee0b964156acfea011b9c3e1dc0129"}]
     */

    private int code;
    /**
     * title : 这就是传说中那场蓄谋已久的复活赛了
     * value : http://www.bilibili.com/video/av6315895/
     * image : http://i0.hdslb.com/bfs/archive/c45d4061435a6bde08f16bbcc9df4d86099defc4.jpg
     * type : 2
     * weight : 2
     * remark :
     * hash : b624927a9866acd49013334209ccfff8
     */

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
