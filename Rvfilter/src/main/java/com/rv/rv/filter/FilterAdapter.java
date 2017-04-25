package com.rv.rv.filter;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.rv.R;
import com.rv.rv.RVHolder;
import com.rv.rv.RVUtils;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * by y on 2017/4/24.
 */

public class FilterAdapter extends RecyclerView.Adapter<RVHolder> {

    public static final int TYPE_LINE = 0;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_ITEM = 2;

    public static final String FESTIVAL = "节日";
    public static final String EMISSION = "排放";
    public static final String WEEK = "星期";
    public static final String COLOR = "颜色";
    public static final String YEARS = "年份";
    public static final String MONTH = "月份";

    private List<FilterBean> list = null;


    private LinkedHashMap<String, String> map = new LinkedHashMap<>();

    public FilterAdapter(List<FilterBean> list) {
        this.list = list;
        for (FilterBean filterBean : list) {
            if (filterBean.isSelect) {
                map.put(filterBean.titleType, filterBean.content);
            }
        }
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LINE:
                return new RVHolder(RVUtils.getView(parent, R.layout.item_filter_line));
            case TYPE_TITLE:
                return new RVHolder(RVUtils.getView(parent, R.layout.item_filter_title));
            default:
                return new RVHolder(RVUtils.getView(parent, R.layout.item_filter_item));
        }
    }

    @Override
    public void onBindViewHolder(RVHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (list == null) {
            return;
        }
        final FilterBean filterBean = list.get(position);
        switch (getItemViewType(position)) {
            case TYPE_ITEM:

                AppCompatTextView tv = holder.get(R.id.filter_tv_content);
                tv.setText(filterBean.content);

                tv.setSelected(TextUtils.equals(list.get(position).content, map.get(filterBean.titleType)));

                tv.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!v.isSelected()) {
                                    map.put(filterBean.titleType, filterBean.content);
                                }
                                notifyDataSetChanged();
                            }
                        });

                break;
            case TYPE_TITLE:
                holder.setTextView(R.id.filter_tv_title, filterBean.titleType);
                break;
            default:
                break;
        }
    }

    public LinkedHashMap<String, String> getSelectBean() {
        return map;
    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position).type;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) != TYPE_ITEM) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }
}
