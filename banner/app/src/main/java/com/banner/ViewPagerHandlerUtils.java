package com.banner;

import android.os.Handler;
import android.os.Message;

/**
 * by y on 2016/9/15.
 */
public class ViewPagerHandlerUtils extends Handler {

    public static final int MSG_START = 0; //开始轮播

    public static final int MSG_UPDATE = 1; //更新
    public static final int MSG_KEEP = 2; //暂停
    public static final int MSG_BREAK = 3; // 恢复
    public static final int MSG_PAGE = 4; //最新的page
    public static final long MSG_DELAY = 2000; //time
    private ViewPagerCurrent mCurrent;
    private int page = 0;
    private boolean isStart = true;

    public ViewPagerHandlerUtils(ViewPagerCurrent viewPager, int currentItem) {
        this.page = currentItem;
        this.mCurrent = viewPager;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (hasMessages(MSG_UPDATE)) {
            removeMessages(MSG_UPDATE);
        }
        if (null == mCurrent) {
            return;
        }
        if (!isStart) {
            return;
        }
        switch (msg.what) {
            case MSG_START:
                sendEmptyMessageDelayed(MSG_UPDATE, MSG_DELAY);
                break;
            case MSG_UPDATE:
                mCurrent.setCurrentItem(++page);
                sendEmptyMessageDelayed(MSG_UPDATE, MSG_DELAY);
                break;
            case MSG_PAGE:
                page = msg.arg1;
                break;
            case MSG_KEEP:
                break;
            case MSG_BREAK:
                sendEmptyMessageDelayed(MSG_UPDATE, MSG_DELAY);
                break;
            default:
                sendEmptyMessageDelayed(MSG_UPDATE, MSG_DELAY);
                break;
        }
    }

    public interface ViewPagerCurrent {
        void setCurrentItem(int page);
    }
}