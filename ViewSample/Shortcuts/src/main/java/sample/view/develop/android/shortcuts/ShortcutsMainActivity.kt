package sample.view.develop.android.shortcuts

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import kotlinx.android.synthetic.main.shortcuts_activity_main.*
import java.util.*

/**
 * 动态添加 小组件
 */
@RequiresApi(api = Build.VERSION_CODES.N_MR1)
class ShortcutsMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shortcuts_activity_main)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return
        }
        val shortcutInfoList = ArrayList<ShortcutInfo>()
        val shortcutManager = getSystemService(ShortcutManager::class.java)
        val blogShortcut = ShortcutInfo.Builder(this, "blog")
            .setShortLabel("my blog")
            .setLongLabel("我的博客")
            .setDisabledMessage("被删除了")
            .setIcon(Icon.createWithResource(applicationContext, R.drawable.ic_launcher_background))
            .setIntent(Intent(Intent.ACTION_VIEW, Uri.parse("https://7449.github.io/")))
            .build()

        val githubShortcut = ShortcutInfo.Builder(this, "github")
            .setShortLabel("my github")
            .setLongLabel("我的github")
            .setDisabledMessage("被删除了")
            .setIcon(Icon.createWithResource(applicationContext, R.drawable.ic_launcher_background))
            .setIntent(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/7449")))
            .build()

        val intent = Intent(this, ShortcutsTestActivity::class.java)
        intent.action = Intent.ACTION_VIEW
        val newActivityShortcut = ShortcutInfo.Builder(this, "Activity")
            .setShortLabel("newActivity")
            .setLongLabel("newActivity")
            .setDisabledMessage("被删除了")
            .setIcon(Icon.createWithResource(applicationContext, R.drawable.ic_launcher_background))
            .setIntent(intent)
            .build()
        shortcutInfoList.add(blogShortcut)
        shortcutInfoList.add(githubShortcut)
        shortcutInfoList.add(newActivityShortcut)

        shortcutManager.dynamicShortcuts = shortcutInfoList

        delete.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                val dynamicShortcuts = shortcutManager.dynamicShortcuts
                for (i in dynamicShortcuts.indices) {
                    val shortcutInfo = dynamicShortcuts[i]
                    if (TextUtils.equals("github", shortcutInfo.id)) {
                        //桌面的图标这个时候就变灰了
                        shortcutManager.disableShortcuts(listOf(shortcutInfo.id))
                        shortcutManager.removeDynamicShortcuts(listOf(shortcutInfo.id))
                    }
                }
            }
        }
    }
}
