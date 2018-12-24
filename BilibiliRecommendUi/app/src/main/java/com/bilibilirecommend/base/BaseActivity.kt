package com.bilibilirecommend.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), BaseFragment.BackHandledInterface {

    var baseFragment: BaseFragment? = null
    var mBundle: Bundle? = null

    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        mBundle = intent.extras
        initById()
        initCreate(savedInstanceState)
    }


    override fun setSelectedFragment(selectedFragment: BaseFragment) {
        this.baseFragment = selectedFragment
    }

    protected abstract fun initCreate(savedInstanceState: Bundle?)

    protected abstract fun initById()

    override fun onBackPressed() {
        if (baseFragment == null || baseFragment?.onBackPressed() != true) {
            if (supportFragmentManager.backStackEntryCount == 0) {
                super.onBackPressed()
            } else {
                supportFragmentManager.popBackStack()
            }
        }
    }

}
