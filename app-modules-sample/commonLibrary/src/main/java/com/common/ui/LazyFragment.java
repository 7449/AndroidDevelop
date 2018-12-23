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
    private boolean isLazyVisible = true;
    private boolean isFirstLoad;
    private boolean isInitView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(getLayoutId(), container, false);
        isInitView = true;
        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isLazyVisible = isVisibleToUser;
        if (isVisibleToUser) {
            lazyData();
        }
    }

    protected void lazyData() {
        if (isFirstLoad || !isLazyVisible || !isInitView) {
            return;
        }
        initActivityCreated();
        isFirstLoad = true;
    }

    protected abstract void initActivityCreated();

    protected abstract int getLayoutId();
}
