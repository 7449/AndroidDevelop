package com.xadapter.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xadapter.OnFooterClickListener
import com.xadapter.OnXAdapterListener
import com.xadapter.OnXBindListener
import com.xadapter.XLoadMoreView
import com.xadapter.holder.XViewHolder
import io.reactivex.network.RxNetWork
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_success.*

class MainActivity : AppCompatActivity(), OnXAdapterListener, OnXBindListener<Entity>, MainView {

    private lateinit var mAdapter: SimpleAdapter<Entity>
    private lateinit var mainPresenter: MainPresenterImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mAdapter = SimpleAdapter()
        mAdapter.apply {
            onFooterListener = object : OnFooterClickListener {
                override fun onXFooterClick(view: View) {
                    if (loadMoreState == XLoadMoreView.ERROR) {
                        loadMoreState = XLoadMoreView.LOAD
                        onXLoadMore()
                    }
                }
            }
            recyclerView = this@MainActivity.recyclerView
            itemLayoutId = R.layout.item_adapter
            pullRefreshEnabled = true
            loadingMoreEnabled = true
            onXBindListener = this@MainActivity
            xAdapterListener = this@MainActivity
        }
        recyclerView.adapter = mAdapter
        mainPresenter = MainPresenterImpl(this)
        mainPresenter.onNetRequest(page, SimpleAdapter.TYPE_STATUS)
    }

    override fun onChangeRootLayoutStatus(status: String) {
        layout_status.setStatus(status)
    }

    override var page: Int = 0

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

    override fun onXLoadMore() {
        mainPresenter.onNetRequest(page, SimpleAdapter.TYPE_LOAD_MORE)
    }

    override fun onXRefresh() {
        page = 0
        mainPresenter.onNetRequest(page, SimpleAdapter.TYPE_REFRESH)
    }
}
