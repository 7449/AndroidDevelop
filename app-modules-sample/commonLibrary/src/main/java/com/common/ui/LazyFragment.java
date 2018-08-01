package com.common.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author y
 */
public abstract class LazyFragment extends Fragment {
    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(getLayoutId(), container, false);
        isInitView = true;
        lazyData();
        return convertView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            isVisible = true;
            lazyData();
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyData() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            return;
        }
        initActivityCreated();
        isFirstLoad = false;
    }

    protected abstract void initActivityCreated();

    protected abstract int getLayoutId();

}
