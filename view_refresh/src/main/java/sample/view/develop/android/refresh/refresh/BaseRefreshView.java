package sample.view.develop.android.refresh.refresh;


import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import sample.view.develop.android.refresh.R;

public abstract class BaseRefreshView extends Drawable implements Drawable.Callback, Animatable {

    private final String statusPullToRefresh;
    private final String statusReleaseToLoad;
    private final String statusRefreshing;
    private final String statusRefreshSuccess;
    private final String statusRefreshFail;

    private PullToRefreshView mRefreshLayout;

    public BaseRefreshView(Context context, PullToRefreshView layout) {
        mRefreshLayout = layout;
        statusPullToRefresh = context.getString(R.string.notify_pull_to_refresh);
        statusReleaseToLoad = context.getString(R.string.notify_release_to_load);
        statusRefreshing = context.getString(R.string.notify_refreshing);
        statusRefreshSuccess = context.getString(R.string.notify_refresh_success);
        statusRefreshFail = context.getString(R.string.notify_refresh_fail);
    }

    Context getContext() {
        return mRefreshLayout != null ? mRefreshLayout.getContext() : null;
    }

    public abstract void setPercent(float percent, boolean invalidate);

    public abstract void offsetTopAndBottom(int offset);

    public abstract void setTextColor(int color);

    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    public String getDescription(int status) {
        switch (status) {
            case PullToRefreshView.STATUS_IDLE:
                return statusPullToRefresh;
            case PullToRefreshView.STATUS_REFRESH_SUCCESS:
                return statusRefreshSuccess;
            case PullToRefreshView.STATUS_REFRESH_ERROR:
                return statusRefreshFail;
            case PullToRefreshView.STATUS_PULL_TO_REFRESH:
                return statusPullToRefresh;
            case PullToRefreshView.STATUS_PULL_TO_LOAD:
                return statusReleaseToLoad;
            default:
                return statusRefreshing;

        }
    }
}
