package sample.util.develop.android.dagger.application

import dagger.Module
import dagger.Provides

/**
 * by y on 2017/5/31.
 */
@Module
class AppModel(private val app: App) {
    @Provides
    fun get(): App = app
}
