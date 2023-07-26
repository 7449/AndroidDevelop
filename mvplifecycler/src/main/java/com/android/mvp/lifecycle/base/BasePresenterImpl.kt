package com.android.mvp.lifecycle.base

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.BehaviorSubject

/**
 * @author y
 * @create 2019/3/31
 */
open class BasePresenterImpl<V : BaseIView>(protected var mView: V?) : BaseIPresenter {

    companion object {
        const val TAG = "PRESENTER"
    }

    private val behaviorSubject = BehaviorSubject.create<Lifecycle.Event>()

    protected fun <T> bindLifecycle(): ObservableTransformer<T, T> =
        ObservableTransformer { observable -> observable.takeUntil(behaviorSubject.skipWhile { event -> event !== Lifecycle.Event.ON_DESTROY }) }

    override fun onStop(owner: LifecycleOwner) {
        Log.e(TAG, "onStop")
        behaviorSubject.onNext(Lifecycle.Event.ON_STOP)
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.e(TAG, "onStart")
        behaviorSubject.onNext(Lifecycle.Event.ON_START)
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.e(TAG, "onResume")
        behaviorSubject.onNext(Lifecycle.Event.ON_STOP)
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.e(TAG, "onPause")
        behaviorSubject.onNext(Lifecycle.Event.ON_PAUSE)
    }

    override fun onCreate(owner: LifecycleOwner) {
        Log.e(TAG, "onCreate")
        behaviorSubject.onNext(Lifecycle.Event.ON_CREATE)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.e(TAG, "onDestroy")
        behaviorSubject.onNext(Lifecycle.Event.ON_DESTROY)
        onDestroy()
    }

    override fun onDestroy() {
        if (mView != null) {
            mView = null
        }
    }

}
