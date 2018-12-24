package android.develop.util.sample

import sample.util.develop.android.dagger.application.App
import sample.util.develop.android.error.library.ExceptionHandler

/**
 * by y.
 */
class App : App() {
    override fun onCreate() {
        super.onCreate()
        val mCustomCrashHandler = ExceptionHandler.instance
        mCustomCrashHandler.setCustomCrashHandler(this)
    }
}
