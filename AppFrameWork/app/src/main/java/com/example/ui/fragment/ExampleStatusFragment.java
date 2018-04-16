package com.example.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.widget.status.StatusLayout;
import com.example.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * by y.
 * <p>
 * Description:
 */

public class ExampleStatusFragment extends BaseFragment {

    @BindView(R.id.status_layout)
    StatusLayout mStatus;
    @BindView(R.id.btn_normal)
    AppCompatButton mNormal;
    @BindView(R.id.btn_empty)
    AppCompatButton mEmpty;
    @BindView(R.id.btn_error)
    AppCompatButton mError;
    @BindView(R.id.btn_success)
    AppCompatButton mSuccess;
    @BindView(R.id.btn_loading)
    AppCompatButton mLoading;

    @Override
    protected void initCreate(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState) {
        mStatus.setNorMalView(R.layout.layout_example_normal);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_status_example;
    }

    @OnClick({R.id.btn_normal, R.id.btn_empty, R.id.btn_error, R.id.btn_success, R.id.btn_loading})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_normal:
                mStatus.setNorMalView(R.layout.layout_example_normal);
                break;
            case R.id.btn_empty:
                mStatus.setNorMalView(R.layout.layout_example_empty);
                break;
            case R.id.btn_error:
                mStatus.setNorMalView(R.layout.layout_example_error);
                break;
            case R.id.btn_success:
                mStatus.setNorMalView(R.layout.layout_example_success);
                break;
            case R.id.btn_loading:
                mStatus.setNorMalView(R.layout.layout_example_loading);
                break;
        }
    }
}
