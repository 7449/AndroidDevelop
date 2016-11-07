package github.com.superadapter.superAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

/**
 * by y on 2016/9/29.
 */
public class RecyclerOnTouchListener implements RecyclerView.OnTouchListener {

    private RefreshHeaderLayout refreshHeaderLayout = null;
    private boolean isRefreshHeader = false;
    private float rawY = -1;
    private RefreshInterface refreshInterface = null;

    private static final int DAMP = 4;

    public RecyclerOnTouchListener(RefreshHeaderLayout refreshHeaderLayout, boolean isRefreshHeader, RefreshInterface refreshInterface) {
        this.refreshHeaderLayout = refreshHeaderLayout;
        this.isRefreshHeader = isRefreshHeader;
        this.refreshInterface = refreshInterface;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (null == refreshHeaderLayout || !isRefreshHeader) {
            return false;
        }
        if (rawY == -1) {
            rawY = motionEvent.getRawY();
        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                rawY = motionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = motionEvent.getRawY() - rawY;
                rawY = motionEvent.getRawY();
                if (isTop() && isRefreshHeader) {
                    refreshHeaderLayout.refreshUI(deltaY / DAMP);
                    if (refreshHeaderLayout.getVisibleHeight() > 0 && refreshHeaderLayout.getStates() < RefreshHeaderLayout.STATE_LOADING) {
                        return true;
                    }
                }
                break;
            default:
                rawY = -1;
                if (isTop() && isRefreshHeader && refreshHeaderLayout.releaseAction() && null != refreshInterface) {
                    refreshInterface.onRefresh();
                }
                break;
        }
        return false;
    }

    private boolean isTop() {
        return refreshHeaderLayout.getParent() != null;
    }

    interface RefreshInterface {
        void onRefresh();
    }

}
