package com.expandablelist.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * by y on 2016/11/21
 */
public abstract class SimpleBaseExpandableListAdapter<T> extends BaseExpandableListAdapter {


    protected List<T> expandableListTitle;
    private HashMap<T, List<T>> expandableListDetail;

    public SimpleBaseExpandableListAdapter(List<T> expandableListTitle, HashMap<T, List<T>> expandableListDetail) {
        if (expandableListTitle != null && expandableListDetail != null) {
            this.expandableListDetail = expandableListDetail;
            this.expandableListTitle = expandableListTitle;
        }
    }


    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return expandableListDetail.get(expandableListTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return getGroup(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return getChild(groupPosition, childPosition, isLastChild, convertView, parent);
    }


    abstract View getGroup(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);

    abstract View getChild(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
