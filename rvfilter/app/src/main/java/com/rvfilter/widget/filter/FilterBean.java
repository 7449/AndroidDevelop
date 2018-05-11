package com.rvfilter.widget.filter;

/**
 * by y on 2017/4/24.
 */

public class FilterBean {
    public int type;
    public String titleType;
    public String content;
    public boolean isSelect;

    public FilterBean(int type) {
        this.type = type;
    }

    public FilterBean(int type, String titleType) {
        this.type = type;
        this.titleType = titleType;
    }

    public FilterBean(int type, String titleType, String content) {
        this.type = type;
        this.titleType = titleType;
        this.content = content;
    }

    public FilterBean(int type, String titleType, String content, boolean isSelect) {
        this.type = type;
        this.titleType = titleType;
        this.content = content;
        this.isSelect = isSelect;
    }
}
