package com.readlist.news.widget;

import android.text.Html;

import com.readlist.R;
import com.readlist.news.model.NewsListModel;

import java.util.List;

import framework.base.BaseRecyclerViewAdapter;
import framework.utils.ImageLoaderUtils;

/**
 * by y on 2016/11/9
 */

public class NewsListAdapter extends BaseRecyclerViewAdapter<NewsListModel> {

    public NewsListAdapter(List<NewsListModel> mDatas) {
        super(mDatas);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_news;
    }

    @Override
    protected void onBind(BaseViewHolder holder, int position, NewsListModel data) {
        ImageLoaderUtils.display(holder.getImageView(R.id.list_image), data.getPicUrl());
        holder.setTextView(R.id.list_tv, Html.fromHtml(data.getTitle()));
    }
}
