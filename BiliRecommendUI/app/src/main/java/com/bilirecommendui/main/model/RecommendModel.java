package com.bilirecommendui.main.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * by y on 2016/9/17.
 */
public class RecommendModel {


    private int code;

    private List<ResultBean> result;

    public int getItemPositionCount() {
        return itemPositionCount;
    }

    public void setItemPositionCount(int itemPositionCount) {
        this.itemPositionCount = itemPositionCount;
    }

    private int itemPositionCount;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }


    public HashMap<Integer, RecommendAdapterTagModel> getMaps() {
        return maps;
    }

    public void setMaps(HashMap<Integer, RecommendAdapterTagModel> maps) {
        this.maps = maps;
    }

    public HashMap<Integer, RecommendAdapterTagModel> maps;

    public static class RecommendAdapterTagModel {
        int position;
        String type;
        int itemPosition;

        public int getImagePage() {
            return imagePage;
        }

        public void setImagePage(int imagePage) {
            this.imagePage = imagePage;
        }

        int imagePage;

        public RecommendAdapterTagModel(int position, String type, int itemPosition, int imagePage) {
            this.position = position;
            this.type = type;
            this.itemPosition = itemPosition;
            this.imagePage = imagePage;
        }


        public int getItemPosition() {
            return itemPosition;
        }

        public void setItemPosition(int itemPosition) {
            this.itemPosition = itemPosition;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class ResultBean {
        private String type;
        /**
         * param :
         * goto :
         * style : gm_av
         * title : 热门焦点
         */

        private HeadBean head;
        /**
         * title : 看起来很干净的夏末清爽妆
         * style : gm_av
         * cover : http://i0.hdslb.com/bfs/archive/3c6c7487f394e8469201b1833c633f203c6f58fa.png
         * param : 6248933
         * goto : av
         * width : 350
         * height : 219
         * play : 5.1万
         * danmaku : 360
         */

        private List<BodyBean> body;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public HeadBean getHead() {
            return head;
        }

        public void setHead(HeadBean head) {
            this.head = head;
        }

        public List<BodyBean> getBody() {
            return body;
        }

        public void setBody(List<BodyBean> body) {
            this.body = body;
        }

        public static class HeadBean {
            private String param;
            @SerializedName("goto")
            private String gotoX;
            private String style;
            private String title;
            private int count;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getGotoX() {
                return gotoX;
            }

            public void setGotoX(String gotoX) {
                this.gotoX = gotoX;
            }

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class BodyBean {
            private String title;
            private String style;
            private String cover;
            private String param;
            @SerializedName("goto")
            private String gotoX;
            private int width;
            private int height;
            private String play;
            private String danmaku;
            private String up;
            private int online;
            private String area;
            private int area_id;
            private String desc1;
            private int status;

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getDesc1() {
                return desc1;
            }

            public void setDesc1(String desc1) {
                this.desc1 = desc1;
            }


            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
                this.area_id = area_id;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }


            public String getUp() {
                return up;
            }

            public void setUp(String up) {
                this.up = up;
            }


            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getParam() {
                return param;
            }

            public void setParam(String param) {
                this.param = param;
            }

            public String getGotoX() {
                return gotoX;
            }

            public void setGotoX(String gotoX) {
                this.gotoX = gotoX;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getPlay() {
                return play;
            }

            public void setPlay(String play) {
                this.play = play;
            }

            public String getDanmaku() {
                return danmaku;
            }

            public void setDanmaku(String danmaku) {
                this.danmaku = danmaku;
            }
        }
    }
}
