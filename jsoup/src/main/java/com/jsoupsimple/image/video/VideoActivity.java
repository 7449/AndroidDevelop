package com.jsoupsimple.image.video;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.jsoupsimple.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import framework.base.BaseActivity;
import framework.utils.UIUtils;

/**
 * by y on 2016/9/26.
 */

public class VideoActivity extends BaseActivity {

    private JCVideoPlayerStandard jcVideoPlayerStandard;
    private TextView tvInfo;

    public static void startActivity(String url, String info) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("info", info);
        UIUtils.startActivity(VideoActivity.class, bundle);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        hideStatusBar();
        setTranslucentForImageView(jcVideoPlayerStandard);
        tvInfo.setText(getIntent().getExtras().getString("info"));
        jcVideoPlayerStandard.setUp(getIntent().getExtras().getString("url"), JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
        jcVideoPlayerStandard.startButton.performClick();
    }

    @Override
    protected void initById() {
        jcVideoPlayerStandard = getView(R.id.jc);
        tvInfo = getView(R.id.tv_video_info);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    private void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    private void setTranslucentForImageView(View marginView) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup.MarginLayoutParams vmlp = (ViewGroup.MarginLayoutParams) marginView.getLayoutParams();
            vmlp.setMargins(0, -(getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"))), 0, 0);
        }
    }
}
