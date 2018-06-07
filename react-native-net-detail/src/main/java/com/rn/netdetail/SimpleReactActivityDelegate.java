package com.rn.netdetail;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactRootView;
import com.rn.netdetail.db.ObjectBoxUtils;
import com.rn.netdetail.floattool.FloatingHelper;

import javax.annotation.Nullable;

public class SimpleReactActivityDelegate extends ReactActivityDelegate {

    private Activity context;
    private ReactRootView mReactRootView;

    public SimpleReactActivityDelegate(Activity activity, @Nullable String mainComponentName, Activity context) {
        super(activity, mainComponentName);
        ObjectBoxUtils.context = context;
        this.context = context;
    }

    @Override
    protected void loadApp(String appKey) {
        if (mReactRootView != null) {
            throw new IllegalStateException("Cannot loadApp while app is already running.");
        }
        mReactRootView = createRootView();
        assert mReactRootView != null;
        mReactRootView.startReactApplication(
                getReactNativeHost().getReactInstanceManager(),
                appKey,
                getLaunchOptions());
//        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_net_detail_list, null);
//        context.setContentView(new FloatingHelper(context, inflate).getView());
        context.setContentView(new FloatingHelper(context, mReactRootView).getView());
    }

    protected void onDestroy() {
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
            mReactRootView = null;
        }
        super.onDestroy();
    }
}
