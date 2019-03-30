package com.android.mvp.lifecycle.base

import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.BehaviorSubject

/**
 * @author y
 * @create 2019/3/31
 */
open class BasePresenterImpl<V : BaseIView>(protected var mView: V?) : BaseIPresenter {
    private val behaviorSubject = BehaviorSubject.create<Lifecycle.Event>()

    protected fun <T> bindLifecycle(): ObservableTransformer<T, T> =
        ObservableTransformer { observable -> observable.takeUntil(behaviorSubject.skipWhile { event -> event !== Lifecycle.Event.ON_DESTROY }) }

    override fun onStop(@NonNull owner: LifecycleOwner) {
        behaviorSubject.onNext(Lifecycle.Event.ON_STOP)
    }

    override fun onStart(@NonNull owner: LifecycleOwner) {
        behaviorSubject.onNext(Lifecycle.Event.ON_START)
    }

    override fun onResume(@NonNull owner: LifecycleOwner) {
        behaviorSubject.onNext(Lifecycle.Event.ON_STOP)
    }

    override fun onPause(@NonNull owner: LifecycleOwner) {
        behaviorSubject.onNext(Lifecycle.Event.ON_PAUSE)
    }

    override fun onCreate(@NonNull owner: LifecycleOwner) {
        behaviorSubject.onNext(Lifecycle.Event.ON_CREATE)
    }

    override fun onDestroy(@NonNull owner: LifecycleOwner) {
        behaviorSubject.onNext(Lifecycle.Event.ON_DESTROY)
        onDestroy()
    }

    override fun onDestroy() {
        if (mView != null) {
            mView = null
        }
    }
}
