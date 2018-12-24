package sample.util.develop.android.dagger.singleton

import dagger.Component

import javax.inject.Singleton

/**
 * by y on 2017/5/31.
 */
@Singleton
@Component
interface SingletonComponent {
    fun register(activity: SingletonActivity)
}
