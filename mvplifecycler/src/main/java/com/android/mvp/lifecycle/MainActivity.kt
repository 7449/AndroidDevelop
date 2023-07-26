package com.android.mvp.lifecycle

import android.os.Bundle
import android.widget.Toast
import com.android.mvp.lifecycle.base.BaseActivity
import com.android.mvp.lifecycle.mvp.MainPresenter
import com.android.mvp.lifecycle.mvp.MainPresenterImpl
import com.android.mvp.lifecycle.mvp.MainView

class MainActivity : BaseActivity<MainPresenter>(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.netWork()
    }

    override fun initPresenter(): MainPresenter = MainPresenterImpl(this)

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showToast() {
        Toast.makeText(this, "show", Toast.LENGTH_LONG).show()
    }

}
