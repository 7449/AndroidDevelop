package framework

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

import framework.utils.SPUtils

/**
 * by y on 2017/2/14
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        SPUtils.init(applicationContext)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null

        val instance: App
            get() = (context as App?)!!
    }
}
