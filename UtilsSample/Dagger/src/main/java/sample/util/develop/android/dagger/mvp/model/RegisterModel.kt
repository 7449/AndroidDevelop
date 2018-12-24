package sample.util.develop.android.dagger.mvp.model

import dagger.Module
import dagger.Provides
import sample.util.develop.android.dagger.mvp.view.MVPView

/**
 * by y on 2017/5/31.
 */
@Module
class RegisterModel(private val mvpView: MVPView) {

    @Provides
    fun provideView() = mvpView
}
