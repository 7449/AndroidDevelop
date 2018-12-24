package sample.util.develop.android.dagger.mvp.view


import dagger.Component
import sample.util.develop.android.dagger.application.App
import sample.util.develop.android.dagger.application.ApplicationComponent
import sample.util.develop.android.dagger.mvp.model.RegisterModel
import sample.util.develop.android.dagger.mvp.widget.MVPActivity

/**
 * by y on 2017/5/31.
 */
@Component(dependencies = [ApplicationComponent::class], modules = [RegisterModel::class])
interface MVPComponent {
    val application: App
    fun register(activity: MVPActivity)
}
