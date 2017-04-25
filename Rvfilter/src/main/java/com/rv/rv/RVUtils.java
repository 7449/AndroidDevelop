package com.rv.rv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rv.rv.filter.FilterAdapter;
import com.rv.rv.filter.FilterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/4/24
 */

public class RVUtils {
    private RVUtils() {
    }


    public static View getView(ViewGroup viewGroup, int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
    }

    public static List<FilterBean> initFilterData() {
        List<FilterBean> filterBeen = new ArrayList<>();
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_TITLE, FilterAdapter.FESTIVAL));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.FESTIVAL, "不限", true));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.FESTIVAL, "端午"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.FESTIVAL, "清明"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.FESTIVAL, "国庆"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.FESTIVAL, "春节"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.FESTIVAL, "元旦"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.FESTIVAL, "圣诞"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.FESTIVAL, "中秋"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_LINE));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_TITLE, FilterAdapter.EMISSION));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.EMISSION, "不限", true));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.EMISSION, "国一"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.EMISSION, "国二"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.EMISSION, "国三"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.EMISSION, "国四"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.EMISSION, "国五"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_LINE));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_TITLE, FilterAdapter.WEEK));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.WEEK, "不限", true));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.WEEK, "星期一"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.WEEK, "星期二"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.WEEK, "星期三"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.WEEK, "星期四"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.WEEK, "星期五"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.WEEK, "星期六"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.WEEK, "星期日"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_LINE));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_TITLE, FilterAdapter.COLOR));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.COLOR, "不限", true));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.COLOR, "黄色"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.COLOR, "红色"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.COLOR, "蓝色"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.COLOR, "绿色"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.COLOR, "棕色"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.COLOR, "黑色"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.COLOR, "白色"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_LINE));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_TITLE, FilterAdapter.YEARS));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.YEARS, "不限", true));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.YEARS, "2017"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.YEARS, "2016"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.YEARS, "2015"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.YEARS, "2014"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.YEARS, "2013"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.YEARS, "2012"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.YEARS, "2011"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_LINE));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_TITLE, FilterAdapter.MONTH));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.MONTH, "不限", true));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.MONTH, "12"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.MONTH, "11"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.MONTH, "10"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.MONTH, "9"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.MONTH, "8"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.MONTH, "7"));
        filterBeen.add(new FilterBean(FilterAdapter.TYPE_ITEM, FilterAdapter.MONTH, "6"));

        return filterBeen;
    }
}
