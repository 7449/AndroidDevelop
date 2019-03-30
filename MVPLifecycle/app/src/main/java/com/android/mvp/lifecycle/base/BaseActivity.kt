package com.android.mvp.lifecycle.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author y
 * @create 2019/3/31
 */
abstract class BaseActivity<P : BaseIPresenter> : AppCompatActivity() {

    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = initPresenter()
        lifecycle.addObserver(presenter)
    }

    protected abstract fun initPresenter(): P

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
