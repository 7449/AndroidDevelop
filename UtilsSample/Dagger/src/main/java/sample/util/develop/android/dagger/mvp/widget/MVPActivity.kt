package sample.util.develop.android.dagger.mvp.widget

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import io.reactivex.network.RxNetWork
import kotlinx.android.synthetic.main.dagger_activity_simple.*
import sample.util.develop.android.dagger.BaseActivity
import sample.util.develop.android.dagger.R
import sample.util.develop.android.dagger.application.App
import sample.util.develop.android.dagger.mvp.model.MVPBean
import sample.util.develop.android.dagger.mvp.model.RegisterModel
import sample.util.develop.android.dagger.mvp.presenter.MVPPresenterImpl
import sample.util.develop.android.dagger.mvp.view.DaggerMVPComponent
import sample.util.develop.android.dagger.mvp.view.MVPView
import java.util.*
import javax.inject.Inject

/**
 * by y on 2017/5/31.
 */

class MVPActivity : BaseActivity(), MVPView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: MVPAdapter

    @Inject
    lateinit var presenter: MVPPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dagger_activity_simple)

        refreshLayout.isEnabled = false
        refreshLayout.setOnRefreshListener(this)
        refreshLayout.post { onRefresh() }

        adapter = MVPAdapter(ArrayList())
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = adapter

        val build = DaggerMVPComponent.builder()
            .registerModel(RegisterModel(this))
            .applicationComponent(App.getBuild(applicationContext as App))
            .build()
        build.register(this)
        val application = build.application
    }

    override fun onRefresh() {
        presenter.startNetWork()
    }

    override fun showProgress() {
        refreshLayout.isRefreshing = true
    }

    override fun hideProgress() {
        refreshLayout.isRefreshing = false
    }

    override fun onNetError() {
        Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
    }

    override fun onNetSuccess(list: List<MVPBean>) {
        adapter.addAll(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.instance.cancelDefaultKey()
    }
}
