package com.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.statusbar.status.StatusBarUtils;


public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private int[] color = new int[]{R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorWhite, R.color.colorBlack};
    private boolean isStatusBarTextColor = false;
    private boolean isStatusBarTextFlymeColor = false;
    private boolean isStatusBarTextMiuiColor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.rootView);

        findViewById(R.id.btn_statusbar_class).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusBarUtils.get((Activity) v.getContext()).reflectAlterStatusBarColor(ContextCompat.getColor(v.getContext(), color[(int) (color.length * Math.random())]));
            }
        });

        findViewById(R.id.btn_statusbar).setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                StatusBarUtils.get((Activity) v.getContext()).alterStatusBarColor(ContextCompat.getColor(getApplicationContext(), color[(int) (color.length * Math.random())]));
            }
        });


        findViewById(R.id.btn_statusbar_text).setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                isStatusBarTextColor = !isStatusBarTextColor;
                linearLayout.setFitsSystemWindows(isStatusBarTextColor);
                StatusBarUtils.get((Activity) v.getContext()).alterStatusBarTextColor(isStatusBarTextColor);
            }
        });


        findViewById(R.id.btn_statusbar_text_flyme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStatusBarTextFlymeColor = !isStatusBarTextFlymeColor;
                StatusBarUtils.get((Activity) v.getContext()).setStatusBarDarkIcon(isStatusBarTextFlymeColor);
//                StatusBarUtils.setStatusBarDarkIcon((Activity) v.getContext(), ContextCompat.getColor(v.getContext(), color[(int) (color.length * Math.random())]));
            }
        });

        findViewById(R.id.btn_statusbar_text_miui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStatusBarTextMiuiColor = !isStatusBarTextMiuiColor;
                StatusBarUtils.get((Activity) v.getContext()).miuiSetStatusBarLightMode(isStatusBarTextMiuiColor);
            }
        });
    }
}
