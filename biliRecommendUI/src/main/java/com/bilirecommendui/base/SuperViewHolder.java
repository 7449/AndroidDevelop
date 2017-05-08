package com.bilirecommendui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bannerlayout.widget.BannerLayout;

/**
 * by y on 2016/9/16.
 */
public class SuperViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewSparseArray;
    private Context context;

    public SuperViewHolder(final View itemView) {
        super(itemView);
        viewSparseArray = new SparseArray<>();
        context = itemView.getContext();
        itemView.setTag(viewSparseArray);
    }

    public <T extends View> T get(int id) {
        View childView = viewSparseArray.get(id);
        if (childView == null) {
            childView = itemView.findViewById(id);
            viewSparseArray.put(id, childView);
        }
        return (T) childView;
    }

    public Context getContext() {
        return context;
    }

    public RecyclerView getRecyclerView(int id) {
        return get(id);
    }

    public FrameLayout getFrameLayout(int id) {
        return get(id);
    }

    public BannerLayout getBannerLayout(int id) {
        return get(id);
    }

    public RelativeLayout getRelativeLayout(int id) {
        return get(id);
    }

    public TextView getTextView(int id) {
        return get(id);
    }

    public ImageView getImageView(int id) {
        return get(id);
    }

    public void setTextView(int id, CharSequence charSequence) {
        getTextView(id).setText(charSequence);
    }

    public void setTextColor(int id, int color) {
        getTextView(id).setTextColor(color);
    }

}
