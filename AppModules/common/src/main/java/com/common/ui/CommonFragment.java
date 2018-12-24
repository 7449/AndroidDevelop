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
import com.status.layout.OnStatusClickListener;
import com.status.layout.StatusLayout;

import org.jetbrains.annotations.NotNull;

/**
 * by y.
 * <p>
 * Description:V4 Fragment
 */

public abstract class CommonFragment extends Fragment implements OnStatusClickListener {

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
        mStatusLayout.addSuccessView(getLayoutId());
        mStatusLayout.setOnStatusClickListener(this);
        initById();
        initCreate(inflater, container, savedInstanceState);
        return mStatusLayout;
    }

    protected abstract void initById();

    protected abstract void initCreate(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected <T extends View> T getId(int id) {
        return mStatusLayout.findViewById(id);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onEmptyClick(@NotNull View view) {

    }

    @Override
    public void onErrorClick(@NotNull View view) {

    }

    @Override
    public void onLoadingClick(@NotNull View view) {

    }

    @Override
    public void onNorMalClick(@NotNull View view) {

    }

    @Override
    public void onSuccessClick(@NotNull View view) {

    }
}
