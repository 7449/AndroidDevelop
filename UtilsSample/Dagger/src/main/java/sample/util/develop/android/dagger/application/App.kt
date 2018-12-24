package sample.util.develop.android.dagger.application

import android.app.Application
import io.reactivex.network.RxNetWork
import io.reactivex.network.SimpleRxNetOptionFactory
import sample.util.develop.android.dagger.mvp.MVPApi

import javax.inject.Inject

/**
 * by y on 2017/5/31.
 */

open class App : Application() {

    @Inject
    lateinit var app: App
    private lateinit var build: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        RxNetWork.initialization(SimpleRxNetOptionFactory(MVPApi.ZL_BASE_API, null, null))
        build = DaggerApplicationComponent
            .builder()
            .appModel(AppModel(this))
            .build()
        build.register(this)
    }

    companion object {
        fun getBuild(app: App) = app.build
    }
}
