package android.develop.objectbox

import android.app.Application
import objectbox.develop.android.entity.DaoSession
import objectbox.develop.android.entity.MyObjectBox
import objectbox.develop.android.multitable.MultiTableObjectBoxUtils
import objectbox.develop.android.two.ObjectBox2xUtils

/**
 * @author y
 * @create 2018/12/23
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val build = MyObjectBox.builder().androidContext(applicationContext).build()
        MultiTableObjectBoxUtils.boxStore = build
        ObjectBox2xUtils.boxStore = build
        ObjectBox2xUtils.dao = DaoSession(build)
    }
}
