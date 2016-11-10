package com.readlist.weixin.model;

import java.util.List;

/**
 * by y on 2016/11/8
 */

public class WXHotModel {


    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-08-29","title":"《盗墓笔记》近10亿票房夺冠暑期档，这才是超级IP的正确打开方式","description":"热荐电影","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-7781842.jpg/640","url":"http://mp.weixin.qq.com/s?__biz=MjM5NTAzNzE4MA==&idx=2&mid=2652532204&sn=f23f620b094fcbdb75c60a86289e57d4"},{"ctime":"2016-08-11","title":"浮生一梦解迷津你真能看懂《盗墓笔记》？","description":"电影迷","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-7360882.jpg/640","url":"http://mp.weixin.qq.com/s?__biz=MzA4NjUxNzYyMg==&idx=1&mid=2654471540&sn=c05f3e77e973374f3065eec9c9429b2b"},{"ctime":"2016-08-11","title":"昨晚看了电影《盗墓笔记》，居然......","description":"电影之家","picUrl":"http://t1.qpic.cn/mblogpic/f01a972dbcc1060fd456/2000","url":"http://mp.weixin.qq.com/s?__biz=MzA4NTExODcxMA==&idx=1&mid=2649788143&sn=b06d56e9889e0ee7eeb8b2de05db3784"},{"ctime":"2016-08-11","title":"《盗墓笔记》成暑期票房救市之作！我有票，来约","description":"娱乐乐翻天","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-7118157.jpg/640","url":"http://mp.weixin.qq.com/s?__biz=MzA3MjgyNTcwNA==&idx=5&mid=2650983885&sn=1c74e69f92e231ffdf2e39948698ec59"},{"ctime":"2016-08-10","title":"为啥三叔自己导《盗墓笔记》原著粉生物也觉得不正宗","description":"黄啸的橙子林","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-7333033.jpg/640","url":"http://mp.weixin.qq.com/s?__biz=MjM5MzI4MjAwNw==&idx=1&mid=2650789473&sn=58069a5b278aa46499e0beb15198d8d4"},{"ctime":"2016-08-10","title":"《盗墓笔记》成暑期票房救市之作！我有票，来约","description":"娱乐乐翻天","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-7270708.jpg/640","url":"http://mp.weixin.qq.com/s?__biz=MzA3MjgyNTcwNA==&idx=5&mid=2650983880&sn=19ea3d94b0403aedb168b9cc7dd69677"},{"ctime":"2016-08-10","title":"《盗墓笔记》撩人技能MAX，老稻米感觉身体被掏空了","description":"思想聚焦","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-7312085.jpg/640","url":"http://mp.weixin.qq.com/s?__biz=MjM5ODA0NDMyMA==&idx=2&mid=2702637931&sn=1d7be4cf28b8534d37f22f2ba78a5fd1"},{"ctime":"2016-08-09","title":"【酷评】《盗墓笔记》：除了基情，你还应该了解这些","description":"影视独舌","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-7287963.jpg/640","url":"http://mp.weixin.qq.com/s?__biz=MjM5NzA5MzA3NQ==&idx=2&mid=2652717514&sn=615d48236c5e8fc222abee31dda9fe1c"},{"ctime":"2016-08-09","title":"《盗墓笔记》还玩瓶邪CP，这代表我们始终被压抑的情爱","description":"腾讯娱乐","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-7286915.jpg/640","url":"http://mp.weixin.qq.com/s?__biz=MTA5NTIzNDE2MQ==&idx=3&mid=2653348889&sn=b89f33fd4b7f97b91a45402cb268b573"},{"ctime":"2016-08-09","title":"《盗墓笔记》就得这么拍！","description":"思想聚焦","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-7281587.jpg/640","url":"http://mp.weixin.qq.com/s?__biz=MjM5ODA0NDMyMA==&idx=2&mid=2702637901&sn=707572f331fc4cb3d361ead325d25249"}]
     */

    private int code;
    private String msg;
    /**
     * ctime : 2016-08-29
     * title : 《盗墓笔记》近10亿票房夺冠暑期档，这才是超级IP的正确打开方式
     * description : 热荐电影
     * picUrl : http://zxpic.gtimg.com/infonew/0/wechat_pics_-7781842.jpg/640
     * url : http://mp.weixin.qq.com/s?__biz=MjM5NTAzNzE4MA==&idx=2&mid=2652532204&sn=f23f620b094fcbdb75c60a86289e57d4
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
