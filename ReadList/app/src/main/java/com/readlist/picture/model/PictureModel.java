package com.readlist.picture.model;

import java.util.List;

/**
 * by y on 2016/11/9
 */

public class PictureModel {


    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-03-06 14:11","title":"刘雨欣美女桌面","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/06/m.xxxiao.com_2411c2dfab27e4411a27c16f4f87dd22-760x500.jpg","url":"http://m.xxxiao.com/1811"},{"ctime":"2016-03-06 14:11","title":"超跑女神超清性感美腿车模","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/04/m.xxxiao.com_952ef7f7e44e4bdaf7718d20c99d66ff3-760x500.jpg","url":"http://m.xxxiao.com/606"},{"ctime":"2016-03-06 14:11","title":"美女许允美黑花连裙衣海边写真","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/05/m.xxxiao.com_ac8044b0a0ee6c0ff51fa4ed4df1010e-760x500.jpg","url":"http://m.xxxiao.com/1268"},{"ctime":"2016-03-06 14:11","title":"黛欣霓圆润酥胸内衣自拍照","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/04/m.xxxiao.com_fe9bea92980bb30c7fb9cc3c33c9920411-760x500.jpg","url":"http://m.xxxiao.com/695"},{"ctime":"2016-03-06 14:11","title":"AISS爱丝美女 丝袜美腿\u2026小哲女神","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/04/m.xxxiao.com_be45c01a6ad655cf09937e579c486526-760x500.jpg","url":"http://m.xxxiao.com/971"},{"ctime":"2016-03-06 14:11","title":"清新美眉爱上金鱼唯美写真","description":"美女图片","picUrl":"http://t1.27270.com/uploads/150725/8-150H5103439118.jpg","url":"http://www.27270.com/ent/meinvtupian/2015/51488.html"},{"ctime":"2016-03-06 14:11","title":"长发美女齐贝贝大秀性感火辣身姿","description":"美女图片","picUrl":"http://t1.27270.com/uploads/tu/201507/375/slt.jpg","url":"http://www.27270.com/ent/meinvtupian/2015/122489.html"},{"ctime":"2016-03-06 14:11","title":"嫩模王语纯双手遮乳破黑丝诱惑照","description":"美女图片","picUrl":"http://t1.27270.com/uploads/tu/201507/374/slt.jpg","url":"http://www.27270.com/ent/meinvtupian/2015/122288.html"},{"ctime":"2016-03-06 14:11","title":"火辣红妆爆乳车模张雅琦魅惑壁纸","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/04/m.xxxiao.com_a8d2ec85eaf98407310b72eb73dda2474-760x500.jpg","url":"http://m.xxxiao.com/419"},{"ctime":"2016-03-06 14:11","title":"AISS爱丝沙滩女神","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/04/m.xxxiao.com_0d5fa187dacfced0f2731489399d4343-760x500.jpg","url":"http://m.xxxiao.com/882"}]
     */

    private int code;
    private String msg;
    /**
     * ctime : 2016-03-06 14:11
     * title : 刘雨欣美女桌面
     * description : 美女图片
     * picUrl : http://m.xxxiao.com/wp-content/uploads/sites/3/2015/06/m.xxxiao.com_2411c2dfab27e4411a27c16f4f87dd22-760x500.jpg
     * url : http://m.xxxiao.com/1811
     */

    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
