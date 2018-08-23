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
    public static final String OPEN_LAZY = "fragment://lazy";
    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = true;
    private boolean lazy = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(getLayoutId(), container, false);
        isInitView = true;
        lazyData();
        return convertView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            lazy = getArguments().getBoolean(OPEN_LAZY, true);
        }
        if (!lazy) {
            initActivityCreated();
        }
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
        if (!lazy) {
            return;
        }
        if (!isFirstLoad || !isVisible || !isInitView) {
            return;
        }
        initActivityCreated();
        isFirstLoad = false;
    }

    protected abstract void initActivityCreated();

    protected abstract int getLayoutId();

}
