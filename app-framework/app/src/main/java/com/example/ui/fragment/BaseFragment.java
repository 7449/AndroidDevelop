package com.example.ui.fragment;

import com.common.ui.CommonFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * by y.
 * <p>
 * Description:
 */

public abstract class BaseFragment extends CommonFragment {

    private Unbinder bind;

    @Override
    protected void init() {
//        mStatusLayout.setErrorView(View);
//        mStatusLayout.setEmptyView(View);
//        mStatusLayout.setLoadingView(View);
//        mStatusLayout.setNorMalView(View);
//        mStatusLayout.setStatusClickListener(
//                new StatusClickListener() {
//                    @Override
//                    public void onNorMalClick() {
//
//                    }
//
//                    @Override
//                    public void onLoadingClick() {
//
//                    }
//
//                    @Override
//                    public void onEmptyClick() {
//
//                    }
//
//                    @Override
//                    public void onSuccessClick() {
//
//                    }
//
//                    @Override
//                    public void onErrorClick() {
//
//                    }
//                });
        bind = ButterKnife.bind(this, inflate);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
