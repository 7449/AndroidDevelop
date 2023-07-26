package android.develop.util.sample

import android.app.Application
import sample.util.develop.android.error.library.ExceptionHandler

/**
 * by y.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val mCustomCrashHandler = ExceptionHandler.instance
        mCustomCrashHandler.setCustomCrashHandler(this)
    }
}
