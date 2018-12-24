package com.xadapter.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xadapter.OnXAdapterListener
import com.xadapter.OnXBindListener
import com.xadapter.holder.XViewHolder
import io.reactivex.network.RxNetWork
import kotlinx.android.synthetic.main.activity_refresh.*

/**
 * @author y
 */
class RefreshActivity : AppCompatActivity(), OnXBindListener<Entity>, MainView, OnXAdapterListener {

    private lateinit var mainPresenter: MainPresenterImpl
    private lateinit var mAdapter: SimpleRefreshAdapter<Entity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mainPresenter = MainPresenterImpl(this)

        mAdapter = SimpleRefreshAdapter<Entity>(swipeRefreshLayout)
                .apply {
                    recyclerView = this@RefreshActivity.recyclerView
                    itemLayoutId = R.layout.item_adapter
                    loadingMoreEnabled = true
                    xAdapterListener = this@RefreshActivity
                    onXBindListener = this@RefreshActivity
                }
        recyclerView.adapter = mAdapter
        mainPresenter.onNetRequest(page, SimpleRefreshAdapter.TYPE_STATUS)
    }


    override fun onXBind(holder: XViewHolder, position: Int, entity: Entity) {
        Glide
                .with(holder.context)
                .load(entity.titleImage)
                .apply(RequestOptions.centerCropTransform())
                .into(holder.getImageView(R.id.list_image))
        holder.setTextView(R.id.list_tv, entity.title)
        holder.setTextView(R.id.list_pos, position.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        RxNetWork.instance.cancelAll()
    }

    override var page: Int = 0

    override fun onChangeRootLayoutStatus(status: String) {
        swipeRefreshLayout.isRefreshing = false
        //don
    }

    override fun onRemoveAll() {
        mAdapter.removeAll()
    }

    override fun onNetComplete(type: Int) {
        mAdapter.onComplete(type)
    }

    override fun onNetError(type: Int) {
        mAdapter.onError(type)
    }

    override fun onLoadNoMore() {
        mAdapter.loadNoMore()
    }

    override fun onNetSuccess(entity: List<Entity>) {
        mAdapter.addAll(entity)
    }

    override fun onPagePlus() {
        page++
    }

    override fun onXLoadMore() {
        mainPresenter.onNetRequest(page, SimpleRefreshAdapter.TYPE_LOAD_MORE)
    }

    override fun onXRefresh() {
        page = 0
        mainPresenter.onNetRequest(page, SimpleRefreshAdapter.TYPE_REFRESH)
    }
}
