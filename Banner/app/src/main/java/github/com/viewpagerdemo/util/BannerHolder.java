package github.com.viewpagerdemo.util;

import android.content.Context;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import github.com.viewpagerdemo.R;
import github.com.viewpagerdemo.ViewPagerHandlerUtils;

/**
 * by y on 2016/9/18.
 */
public class BannerHolder implements ViewPagerHandlerUtils.ViewPagerCurrent {


    private int preEnablePosition = 0;
    private ViewPager mViewPager;
    private ViewPagerHandlerUtils mHandlerUtil;
    private LinearLayout mLinearLayout;

    public <T> void setBanner(final List<T> banner, BannerInterface<T> bannerInterface) {
        //初始化viewpager 小圆点 adapter
        initHolder(banner, bannerInterface);

        //这里给一个无限大的正确的数字 这样viewpager刚开始就可以向右滑动
        mViewPager.setCurrentItem((Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % banner.size()));

        //初始化轮播HandlerUtils
        mHandlerUtil = new ViewPagerHandlerUtils(this, mViewPager.getCurrentItem());
        //提供了setStart() 这个方法 如果想实现 在发送消息之前设置为false就行了

        //刚开始发送一个开始轮播的消息 这里可以判断图片的数量 如果是一个 就不轮播
        mHandlerUtil.sendEmptyMessage(ViewPagerHandlerUtils.MSG_START);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int newPosition = position % banner.size();
                mLinearLayout.getChildAt(preEnablePosition).setEnabled(false);
                mLinearLayout.getChildAt(newPosition).setEnabled(true);
                preEnablePosition = newPosition;
                mHandlerUtil.sendMessage(Message.obtain(mHandlerUtil, ViewPagerHandlerUtils.MSG_PAGE, mViewPager.getCurrentItem(), 0));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mHandlerUtil.sendEmptyMessage(ViewPagerHandlerUtils.MSG_KEEP);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        mHandlerUtil.sendEmptyMessageDelayed(ViewPagerHandlerUtils.MSG_UPDATE, ViewPagerHandlerUtils.MSG_DELAY);
                        break;
                }
            }
        });
    }

    @Override
    public void setCurrentItem(int page) {
        mViewPager.setCurrentItem(page);
    }

    private <T> void initHolder(List<T> banner, BannerInterface<T> bannerInterface) {
        mViewPager = bannerInterface.getViewPager();
        mLinearLayout = bannerInterface.getLinearLayout();
        BasePagerAdapter adapter = bannerInterface.getBannerAdapter(banner);
        initRound(bannerInterface.getContext(), banner.size());
        mViewPager.setAdapter(adapter);
    }

    private void initRound(Context context, int bannerCount) {
        if (null != mLinearLayout) {
            mLinearLayout.removeAllViews();
            for (int i = 0; i < bannerCount; i++) {
                View view = new View(context);
                view.setBackgroundResource(R.drawable.point_background);
                if (i == 0) {
                    view.setEnabled(true);
                } else {
                    view.setEnabled(false);
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
                view.setLayoutParams(params);
                params.leftMargin = 10;
                mLinearLayout.addView(view);
            }
        }
    }

}
