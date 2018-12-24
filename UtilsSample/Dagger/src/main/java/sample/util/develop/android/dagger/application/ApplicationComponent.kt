package sample.util.develop.android.dagger.application

import dagger.Component

/**
 * by y on 2017/5/31.
 */
@Component(modules = [AppModel::class])
interface ApplicationComponent {
    val application: App
    fun register(app: App)
}
