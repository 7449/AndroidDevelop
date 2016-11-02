package com.codekk.p.search.model;

import java.util.List;

/**
 * by y on 2016/8/30.
 */
public class SearchModel {


    /**
     * isFullSearch : false
     * projectArray : [{"lang":"Android","committer":"Trinea","updateTime":"2015-09-01T02:24:00","hide":false,"demoUrl":"https://github.com/Trinea/TrineaDownload/blob/master/pull-to-refreshview-demo.apk?raw=true","projectName":"Android-PullToRefresh","smallType":"一、ListView","apps":"新浪微博各个页面","bigType":"第一部分 个性化控件(View)","usedTimes":0,"source":"GitHub","authorName":"chrisbanes","expiredTimes":0,"voteUp":0,"projectUrl":"https://github.com/chrisbanes/Android-PullToRefresh","recommend":false,"desc":"一个强大的拉动刷新开源项目，支持各种控件下拉刷新，ListView、ViewPager、WebView、ExpandableListView、GridView、ScrollView、Horizontal ScrollView、Fragment 上下左右拉动刷新，比下面 johannilsson 那个只支持 ListView 的强大的多。并且它实现的下拉刷新 ListView 在 item 不足一屏情况下也不会显示刷新提示，体验更好。","createTime":"2015-09-01T02:24:00","tags":[{"userName":"Trinea","contentId":"55edafd7d6459ae793499b74","type":"open-source-project","createTime":"2015-11-19T03:16:33.683000","name":"下拉刷新"}],"authorUrl":"https:/github.com/chrisbanes","_id":"55edafd7d6459ae793499b74","codeKKUrl":"http://p.codekk.com/detail/Android/chrisbanes/Android-PullToRefresh"}]
     * totalCount : 50
     * pageSize : 100
     */

    private boolean isFullSearch;
    private int totalCount;
    private int pageSize;
    /**
     * lang : Android
     * committer : Trinea
     * updateTime : 2015-09-01T02:24:00
     * hide : false
     * demoUrl : https://github.com/Trinea/TrineaDownload/blob/master/pull-to-refreshview-demo.apk?raw=true
     * projectName : Android-PullToRefresh
     * smallType : 一、ListView
     * apps : 新浪微博各个页面
     * bigType : 第一部分 个性化控件(View)
     * usedTimes : 0
     * source : GitHub
     * authorName : chrisbanes
     * expiredTimes : 0
     * voteUp : 0
     * projectUrl : https://github.com/chrisbanes/Android-PullToRefresh
     * recommend : false
     * desc : 一个强大的拉动刷新开源项目，支持各种控件下拉刷新，ListView、ViewPager、WebView、ExpandableListView、GridView、ScrollView、Horizontal ScrollView、Fragment 上下左右拉动刷新，比下面 johannilsson 那个只支持 ListView 的强大的多。并且它实现的下拉刷新 ListView 在 item 不足一屏情况下也不会显示刷新提示，体验更好。
     * createTime : 2015-09-01T02:24:00
     * tags : [{"userName":"Trinea","contentId":"55edafd7d6459ae793499b74","type":"open-source-project","createTime":"2015-11-19T03:16:33.683000","name":"下拉刷新"}]
     * authorUrl : https:/github.com/chrisbanes
     * _id : 55edafd7d6459ae793499b74
     * codeKKUrl : http://p.codekk.com/detail/Android/chrisbanes/Android-PullToRefresh
     */

    private List<ProjectArrayBean> projectArray;

    public boolean isIsFullSearch() {
        return isFullSearch;
    }

    public void setIsFullSearch(boolean isFullSearch) {
        this.isFullSearch = isFullSearch;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ProjectArrayBean> getProjectArray() {
        return projectArray;
    }

    public void setProjectArray(List<ProjectArrayBean> projectArray) {
        this.projectArray = projectArray;
    }

    public static class ProjectArrayBean {
        private String lang;
        private String committer;
        private String updateTime;
        private boolean hide;
        private String demoUrl;
        private String projectName;
        private String smallType;
        private String apps;
        private String bigType;
        private int usedTimes;
        private String source;
        private String authorName;
        private int expiredTimes;
        private int voteUp;
        private String projectUrl;
        private boolean recommend;
        private String desc;
        private String createTime;
        private String authorUrl;
        private String _id;
        private String codeKKUrl;
        /**
         * userName : Trinea
         * contentId : 55edafd7d6459ae793499b74
         * type : open-source-project
         * createTime : 2015-11-19T03:16:33.683000
         * name : 下拉刷新
         */

        private List<TagsBean> tags;

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getCommitter() {
            return committer;
        }

        public void setCommitter(String committer) {
            this.committer = committer;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isHide() {
            return hide;
        }

        public void setHide(boolean hide) {
            this.hide = hide;
        }

        public String getDemoUrl() {
            return demoUrl;
        }

        public void setDemoUrl(String demoUrl) {
            this.demoUrl = demoUrl;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getSmallType() {
            return smallType;
        }

        public void setSmallType(String smallType) {
            this.smallType = smallType;
        }

        public String getApps() {
            return apps;
        }

        public void setApps(String apps) {
            this.apps = apps;
        }

        public String getBigType() {
            return bigType;
        }

        public void setBigType(String bigType) {
            this.bigType = bigType;
        }

        public int getUsedTimes() {
            return usedTimes;
        }

        public void setUsedTimes(int usedTimes) {
            this.usedTimes = usedTimes;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public int getExpiredTimes() {
            return expiredTimes;
        }

        public void setExpiredTimes(int expiredTimes) {
            this.expiredTimes = expiredTimes;
        }

        public int getVoteUp() {
            return voteUp;
        }

        public void setVoteUp(int voteUp) {
            this.voteUp = voteUp;
        }

        public String getProjectUrl() {
            return projectUrl;
        }

        public void setProjectUrl(String projectUrl) {
            this.projectUrl = projectUrl;
        }

        public boolean isRecommend() {
            return recommend;
        }

        public void setRecommend(boolean recommend) {
            this.recommend = recommend;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAuthorUrl() {
            return authorUrl;
        }

        public void setAuthorUrl(String authorUrl) {
            this.authorUrl = authorUrl;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCodeKKUrl() {
            return codeKKUrl;
        }

        public void setCodeKKUrl(String codeKKUrl) {
            this.codeKKUrl = codeKKUrl;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            private String userName;
            private String contentId;
            private String type;
            private String createTime;
            private String name;

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getContentId() {
                return contentId;
            }

            public void setContentId(String contentId) {
                this.contentId = contentId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
