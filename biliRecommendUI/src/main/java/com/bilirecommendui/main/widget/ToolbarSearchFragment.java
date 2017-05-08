package com.bilirecommendui.main.widget;


import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;

import com.bilirecommendui.R;
import com.bilirecommendui.base.SuperFragment;
import com.bilirecommendui.utils.UIUtils;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealLinearLayout;

import static com.bilirecommendui.utils.UIUtils.hypo;

/**
 * by y on 2016/9/21.
 * <p>
 * 这种方法同样可以检测到Back的事件
 * <p>
 * //    @Override
 * //    public void onResume() {
 * //        super.onResume();
 * //        View view = getView();
 * //        if (view != null) {
 * //            view.setFocusableInTouchMode(true);
 * //            view.requestFocus();
 * //            view.setOnKeyListener(
 * //                    new View.OnKeyListener() {
 * //                        @Override
 * //                        public boolean onKey(View v, int keyCode, KeyEvent event) {
 * //                            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
 * //                                // 这里进行back键的判断
 * //                                return true;
 * //                            }
 * //                            return false;
 * //                        }
 * //                    });
 * //        }
 * //    }
 */

public class ToolbarSearchFragment extends SuperFragment
        implements View.OnClickListener, ViewTreeObserver.OnPreDrawListener,
        SupportAnimatorListener.AnimatorInterface {

    public static final String TAG = "toolbar_search";
    public static final String BACK_STACK = "fragment:reveal";

    private RevealLinearLayout mRevealLayout;
    private EditText mSearch;

    private ImageView mSearchView;
    private int centerX;
    private int centerY;

    public static ToolbarSearchFragment startFragment() {
        return new ToolbarSearchFragment();
    }


    @Override
    protected void initById() {
        getView(R.id.iv_finish).setOnClickListener(this);
        mRevealLayout = getView(R.id.reveal_linear_layout);
        mSearch = getView(R.id.et_search);
        mSearchView = getView(R.id.iv_search);
        mSearchView.setOnClickListener(this);
        getView(R.id.iv_scan).setOnClickListener(this);
        getView(R.id.rootView).setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mRevealLayout.getViewTreeObserver().addOnPreDrawListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rootView:
            case R.id.iv_finish:
            case R.id.iv_scan:
                onBackPressed();
                break;
            case R.id.iv_search:
                break;
        }
    }

    @Override
    public boolean onPreDraw() {
        mRevealLayout.getViewTreeObserver().removeOnPreDrawListener(this);
        mRevealLayout.setVisibility(View.GONE);
        centerX = mSearchView.getLeft() + mSearchView.getWidth() / 2;
        centerY = mSearchView.getTop() + mSearchView.getHeight() / 2;
        initAnimator(SupportAnimatorListener.TYPE_START);
        return true;
    }

    @Override
    public void onStartAnimationStart() {
        mRevealLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStartAnimationEnd() {
        mSearch.requestFocus();
        UIUtils.openKeyboard(mSearch);
    }

    @Override
    public void onEndAnimationStart() {
        mRevealLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onEndAnimationEnd() {
        mRevealLayout.setVisibility(View.GONE);
        if (null != getActivity()) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onBackPressed() {
        if (null == mRevealLayout || isVisibility(mRevealLayout)) {
            return false;
        } else {
            initAnimator(SupportAnimatorListener.TYPE_END);
            UIUtils.offKeyboard(mSearch);
            return true;
        }
    }


    private void initAnimator(int type) {
        SupportAnimator mRevealAnimator = ViewAnimationUtils.
                createCircularReveal(mRevealLayout, centerX, centerY, 20, hypo(mRevealLayout.getWidth(), mRevealLayout.getHeight()));
        if (type == SupportAnimatorListener.TYPE_END) {
            mRevealAnimator = mRevealAnimator.reverse();
        }
        mRevealAnimator.addListener(new SupportAnimatorListener(type, this));
        mRevealAnimator.setDuration(500);
        mRevealAnimator.setStartDelay(100);
        mRevealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRevealAnimator.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_toolbar_search;
    }
}
