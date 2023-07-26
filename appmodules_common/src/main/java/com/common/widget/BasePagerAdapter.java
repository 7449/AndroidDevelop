package com.common.widget;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * by y on 2016/5/27.
 */

public abstract class BasePagerAdapter<T> extends PagerAdapter {

    protected List<T> data;

    public BasePagerAdapter(List<T> data) {
        if (data != null) {
            this.data = data;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        return instantiate(container, position, data.get(position));
    }

    protected abstract Object instantiate(ViewGroup container, int position, T data);

}