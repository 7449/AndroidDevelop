package com.bilibilirecommend.main.widget


import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import com.bilibilirecommend.R
import com.bilibilirecommend.base.SuperFragment
import com.bilibilirecommend.utils.UIUtils
import io.codetail.animation.ViewAnimationUtils
import kotlinx.android.synthetic.main.fragment_toolbar_search.*


/**
 *
 * 这种方法同样可以检测到Back的事件
 *
 *
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

open class ToolbarSearchFragment : SuperFragment(), View.OnClickListener, ViewTreeObserver.OnPreDrawListener, SupportAnimatorListener.AnimatorInterface {

    companion object {

        const val TAG = "toolbar_search"
        const val BACK_STACK = "fragment:reveal"

        fun startFragment(): ToolbarSearchFragment {
            return ToolbarSearchFragment()
        }
    }

    private var centerX: Int = 0
    private var centerY: Int = 0

    override val layoutId: Int = R.layout.fragment_toolbar_search

    protected fun initById() {
        iv_finish.setOnClickListener(this)
        iv_search.setOnClickListener(this)
        iv_scan.setOnClickListener(this)
        rootView.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initById()
        reveal_linear_layout.viewTreeObserver.addOnPreDrawListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.rootView, R.id.iv_finish, R.id.iv_scan -> onBackPressed()
            R.id.iv_search -> {
            }
        }
    }

    override fun onPreDraw(): Boolean {
        reveal_linear_layout.viewTreeObserver.removeOnPreDrawListener(this)
        reveal_linear_layout.visibility = View.GONE
        centerX = iv_search.left + iv_search.width / 2
        centerY = iv_search.top + iv_search.height / 2
        initAnimator(SupportAnimatorListener.TYPE_START)
        return true
    }

    override fun onStartAnimationStart() {
        reveal_linear_layout.visibility = View.VISIBLE
    }

    override fun onStartAnimationEnd() {
        et_search.requestFocus()
        UIUtils.openKeyboard(et_search)
    }

    override fun onEndAnimationStart() {
        reveal_linear_layout.visibility = View.VISIBLE
    }


    override fun onEndAnimationEnd() {
        reveal_linear_layout.visibility = View.GONE
        if (null != activity) {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun onBackPressed(): Boolean {
        return if (null == reveal_linear_layout || isVisibility(reveal_linear_layout)) {
            false
        } else {
            initAnimator(SupportAnimatorListener.TYPE_END)
            UIUtils.offKeyboard(et_search)
            true
        }
    }


    private fun initAnimator(type: Int) {
        var mRevealAnimator = ViewAnimationUtils.createCircularReveal(reveal_linear_layout, centerX, centerY, 20f,
                UIUtils.hypo(reveal_linear_layout.width, reveal_linear_layout.height))
        if (type == SupportAnimatorListener.TYPE_END) {
            mRevealAnimator = mRevealAnimator.reverse()
        }
        mRevealAnimator.addListener(SupportAnimatorListener(type, this))
        mRevealAnimator.duration = 500
        mRevealAnimator.startDelay = 100
        mRevealAnimator.interpolator = AccelerateDecelerateInterpolator()
        mRevealAnimator.start()
    }

}
