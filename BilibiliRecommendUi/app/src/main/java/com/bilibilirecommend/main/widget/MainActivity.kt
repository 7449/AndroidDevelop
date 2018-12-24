package com.bilibilirecommend.main.widget


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.bannerlayout.model.BannerModel
import com.bilibilirecommend.R
import com.bilibilirecommend.base.BaseActivity
import com.bilibilirecommend.main.model.RecommendModel
import com.bilibilirecommend.main.presenter.RecommendPresenter
import com.bilibilirecommend.main.presenter.RecommendPresenterImpl
import com.bilibilirecommend.main.view.RecommendView
import com.bilibilirecommend.main.widget.adapter.RecommendAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, RecommendView {

    private lateinit var presenter: RecommendPresenter
    private lateinit var adapter: RecommendAdapter

    override val layoutId: Int = R.layout.activity_main

    override fun initCreate(savedInstanceState: Bundle?) {
        toolbar.title = "推荐"
        setSupportActionBar(toolbar)
        presenter = RecommendPresenterImpl(this)

        adapter = RecommendAdapter()
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        srf_layout.setOnRefreshListener(this)
        srf_layout.setColorSchemeColors(ContextCompat.getColor(baseContext, R.color.colorPrimary))
        srf_layout.post { onRefresh() }
    }

    override fun initById() {
    }

    override fun onRefresh() {
        presenter.netWorkRequest(4)
    }

    override fun setBannerData(bannerData: List<BannerModel>) {
        adapter.setBannerData(bannerData)
    }

    override fun setRecommendData(recommendData: RecommendModel) {
        adapter.refreshData(recommendData)
    }

    override fun removeAdapter() {
    }

    override fun netWorkError() {
        Snackbar.make(activity_main, "网络异常", Snackbar.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        srf_layout.isRefreshing = true
    }

    override fun hideProgress() {
        srf_layout.isRefreshing = false
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_download -> {
            }
            R.id.action_search -> replaceTagFragment(ToolbarSearchFragment.startFragment(), ToolbarSearchFragment.TAG, ToolbarSearchFragment.BACK_STACK)
        }
        return true
    }

    private fun replaceTagFragment(fragment: Fragment, tag: String, backStack: String) {
        supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, fragment, tag)
                .addToBackStack(backStack)
                .commit()
    }

    override fun onBackPressed() {
        if (baseFragment == null || baseFragment?.onBackPressed() != true) {
            if (supportFragmentManager.backStackEntryCount != 0) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }
}
