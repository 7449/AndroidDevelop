package com.shortcuts;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 动态添加 小组件
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return;
        }
        List<ShortcutInfo> shortcutInfoList = new ArrayList<>();
        final ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        ShortcutInfo blogShortcut = new ShortcutInfo.Builder(this, "blog")
                .setShortLabel("my blog")
                .setLongLabel("我的博客")
                .setDisabledMessage("被删除了")
                .setIcon(Icon.createWithResource(getApplicationContext(), R.mipmap.ic_launcher))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://7449.github.io/")))
                .build();

        ShortcutInfo githubShortcut = new ShortcutInfo.Builder(this, "github")
                .setShortLabel("my github")
                .setLongLabel("我的github")
                .setDisabledMessage("被删除了")
                .setIcon(Icon.createWithResource(getApplicationContext(), R.mipmap.ic_launcher))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/7449")))
                .build();

        Intent intent = new Intent(this, TestActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        ShortcutInfo newActivityShortcut = new ShortcutInfo.Builder(this, "Activity")
                .setShortLabel("newActivity")
                .setLongLabel("newActivity")
                .setDisabledMessage("被删除了")
                .setIcon(Icon.createWithResource(getApplicationContext(), R.mipmap.ic_launcher))
                .setIntent(intent)
                .build();
        shortcutInfoList.add(blogShortcut);
        shortcutInfoList.add(githubShortcut);
        shortcutInfoList.add(newActivityShortcut);

        shortcutManager.setDynamicShortcuts(shortcutInfoList);


        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                    List<ShortcutInfo> dynamicShortcuts = shortcutManager.getDynamicShortcuts();
                    for (int i = 0; i < dynamicShortcuts.size(); i++) {

                        ShortcutInfo shortcutInfo = dynamicShortcuts.get(i);

                        if (TextUtils.equals("github", shortcutInfo.getId())) {

                            //桌面的图标这个时候就变灰了

                            shortcutManager.disableShortcuts(Collections.singletonList(shortcutInfo.getId()));
                            shortcutManager.removeDynamicShortcuts(Collections.singletonList(shortcutInfo.getId()));
                        }

                    }
                }
            }
        });
    }
}
