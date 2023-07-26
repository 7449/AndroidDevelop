package com.video.main.main

import android.annotation.SuppressLint
import android.common.core.BasePresenterImpl
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.kotlin.x.findFragmentByTag
import com.kotlin.x.startActivity
import com.video.main.BundleKey
import com.video.main.R
import com.video.main.search.SearchListActivity

interface MainPresenter {
    fun switchId(uiType: Int)
    fun onSearch()
}

class MainPresenterImpl(private val view: MainView) : BasePresenterImpl<MainView>(view),
    MainPresenter {

    override fun switchId(uiType: Int) {
        val activity = view.mainActivity
        val manager = activity.supportFragmentManager
        val transaction = manager.beginTransaction()
        for (fragment in manager.fragments) {
            transaction.hide(fragment)
        }
        val currentFragment =
            activity.findFragmentByTag(uiType.toString()) { MainTabFragment.newInstance(uiType) }
        if (currentFragment.isAdded) {
            transaction.show(currentFragment)
        } else {
            transaction.add(R.id.mainFragment, currentFragment, uiType.toString())
        }
        transaction.commitAllowingStateLoss()
    }

    @SuppressLint("CheckResult")
    override fun onSearch() {
        MaterialDialog(view.mainActivity).show {
            title(R.string.search_input_hint)
            input { _, text ->
                context.startActivity(SearchListActivity::class.java, Bundle().apply {
                    putString(BundleKey.searchKey, text.toString())
                })
            }
            positiveButton(android.R.string.ok)
        }
    }
}