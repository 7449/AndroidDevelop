package com.common.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.R;
import com.common.widget.status.StatusLayout;

/**
 * by y.
 * <p>
 * Description:V4 Fragment
 */

public abstract class CommonFragment extends Fragment {

    protected CoordinatorLayout rootView;
    protected StatusLayout mStatusLayout;
    protected View inflate;

    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = getActivity().findViewById(R.id.activity_root_view);
        }
        inflate = inflater.inflate(R.layout.fragment_common, container, false);
        mStatusLayout = inflate.findViewById(R.id.fragment_status_layout);
        mStatusLayout.setSuccessView(getLayoutId());
        init();
        initCreate(inflater, container, savedInstanceState);
        return mStatusLayout;
    }

    protected abstract void init();

    protected abstract void initCreate(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract int getLayoutId();
}
