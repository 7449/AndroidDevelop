package com.video.main.search

import android.common.core.BasePresenter
import android.common.core.BasePresenterImpl
import android.common.ui.StatusActivity
import android.os.Bundle
import com.video.main.BundleKey
import com.video.main.R
import com.video.main.list.ListUiFragment
import com.video.main.net.VideoUiType

class SearchListActivity : StatusActivity<BasePresenter>(R.layout.fragment_root) {

    override fun initPresenter(): BasePresenter = BasePresenterImpl(null)

    override fun initCreate(savedInstanceState: Bundle?) {
        val search = intent.extras?.getString(BundleKey.searchKey).toString()
        mToolbar.title = search
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_root, ListUiFragment.newInstance(search, VideoUiType.TAGS))
            .commitAllowingStateLoss()
    }

}


