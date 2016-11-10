package com.readlist.news.model;

import java.util.List;

/**
 * by y on 2016/11/9
 */

public class NewsListModel {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-11-09 00:00","title":"期待吗？下一代iPhone SE 将有这三点改进","description":"爱思助手","picUrl":"http://d.image.i4.cn/i4web/image/news/2016-11-09/1478673872364.jpg","url":"http://www.i4.cn/news_detail_11334.html"},{"ctime":"2016-11-09 00:00","title":"苹果新专利曝光：iPhone增强现实地图系统","description":"爱思助手","picUrl":"http://d.image.i4.cn/i4web/image/news/2016-11-09/1478674190375.jpg","url":"http://www.i4.cn/news_detail_11335.html"},{"ctime":"2016-11-09 00:00","title":"iPhone 6S有电自动关机？这个Bug你遇上了没","description":"爱思助手","picUrl":"http://d.image.i4.cn/i4web/image/news/2016-11-09/1478653478287.jpg","url":"http://www.i4.cn/news_detail_11320.html"},{"ctime":"2016-11-09 00:00","title":"苹果美国官网开始出售翻新iPhone6s/Plus  只需3000元","description":"爱思助手","picUrl":"http://d.image.i4.cn/i4web/image/news/2016-11-09/1478653860994.jpg","url":"http://www.i4.cn/news_detail_11321.html"},{"ctime":"2016-11-09 00:00","title":"台积电独家供应A10芯片：营收将达到历年最高水准","description":"爱思助手","picUrl":"http://d.image.i4.cn/i4web/image/news/2016-11-09/1478654095757.jpg","url":"http://www.i4.cn/news_detail_11322.html"},{"ctime":"2016-11-09 00:00","title":"不比不知道！iPhone RAW/JPEG照片对比差别这么大","description":"爱思助手","picUrl":"http://d.image.i4.cn/i4web/image/news/2016-11-09/1478654351102.jpg","url":"http://www.i4.cn/news_detail_11324.html"},{"ctime":"2016-11-09 00:00","title":"iPhone 7备货充足还是销量不佳？富士康工厂已暂停招工","description":"爱思助手","picUrl":"http://d.image.i4.cn/i4web/image/news/2016-11-09/1478654554584.jpg","url":"http://www.i4.cn/news_detail_11325.html"},{"ctime":"2016-11-09 00:00","title":"苹果PCB供应商营收创新高  iPhone 7功不可没","description":"爱思助手","picUrl":"http://d.image.i4.cn/i4web/image/news/2016-11-09/1478654762936.jpg","url":"http://www.i4.cn/news_detail_11326.html"},{"ctime":"2016-11-09 00:00","title":"苹果升级查找我的iPhone：关机也不怕","description":"爱思助手","picUrl":"http://d.image.i4.cn/i4web/image/news/2016-11-09/1478654802281.jpg","url":"http://www.i4.cn/news_detail_11327.html"},{"ctime":"2016-11-09 00:00","title":"iPhone新漏洞被国人利用   日发广告70万","description":"爱思助手","picUrl":"http://d.image.i4.cn/i4web/image/news/2016-11-09/1478656667728.jpg","url":"http://www.i4.cn/news_detail_11328.html"}]
     */

    private int code;
    private String msg;
    /**
     * ctime : 2016-11-09 00:00
     * title : 期待吗？下一代iPhone SE 将有这三点改进
     * description : 爱思助手
     * picUrl : http://d.image.i4.cn/i4web/image/news/2016-11-09/1478673872364.jpg
     * url : http://www.i4.cn/news_detail_11334.html
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
