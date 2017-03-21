package com.codekk.p.search.widget;

import android.text.Html;

import com.codekk.p.R;
import com.codekk.p.search.model.SearchModel;

import java.util.List;

import framework.base.BaseRecyclerViewAdapter;

/**
 * by y on 2016/8/30.
 */
public class SearchAdapter extends BaseRecyclerViewAdapter<SearchModel.ProjectArrayBean> {

    public SearchAdapter(List<SearchModel.ProjectArrayBean> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_search;
    }

    @Override
    protected void onBind(ViewHolder holder, int position, SearchModel.ProjectArrayBean data) {
        holder.setTextView(R.id.tv_author_name, "开源者：" + data.getAuthorName());
        holder.setTextView(R.id.tv_author_url, "个人主页：" + data.getAuthorUrl());
        holder.setTextView(R.id.tv_project_name, "项目名称：" + data.getProjectName());
        //noinspection deprecation
        holder.setTextView(R.id.tv_desc, "简介：" + Html.fromHtml(data.getDesc()));
        holder.setTextView(R.id.tv_project_url, data.getProjectUrl());
    }
}
