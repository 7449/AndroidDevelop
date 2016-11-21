package com.expandablelistsimple.expandablelist;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.expandablelistsimple.R;

import java.util.HashMap;
import java.util.List;

/**
 * by y on 2016/11/21
 */

public class SimpleAdapter extends SimpleBaseExpandableListAdapter<String> {


    public SimpleAdapter(List<String> expandableListTitle, HashMap<String, List<String>> expandableListDetail) {
        super(expandableListTitle, expandableListDetail);
    }

    @Override
    View getGroup(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String data = expandableListTitle.get(groupPosition);


        convertView = View.inflate(parent.getContext(), R.layout.group_item, null);

        TextView textView = (TextView) convertView.findViewById(R.id.tv_group);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv);

        textView.setText(data);

        if (isExpanded) {
            imageView.setBackgroundResource(R.drawable.dropdown);
        } else {
            imageView.setBackgroundResource(R.drawable.select);
        }

        return convertView;
    }

    @Override
    View getChild(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String data = (String) this.getChild(groupPosition, childPosition);

        convertView = View.inflate(parent.getContext(), R.layout.child_item, null);

        TextView tv = (TextView) convertView.findViewById(R.id.tv_child);
        tv.setText(data);
        return convertView;
    }
}
