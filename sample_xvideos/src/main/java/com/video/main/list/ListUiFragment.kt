package com.video.main.list

import android.common.ViewStatus
import android.common.core.shareTextPlain
import android.common.loadNoMore
import android.common.ui.ListFragment
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.kotlin.x.glide
import com.kotlin.x.startActivity
import com.video.main.BundleKey
import com.video.main.R
import com.video.main.detail.DetailUiActivity
import com.video.main.net.ListBaseEntity
import com.video.main.net.VideoListEntity
import com.video.main.search.SearchListActivity
import com.xadapter.addAll
import com.xadapter.getImageView
import com.xadapter.setItemLayoutId
import com.xadapter.setLoadMoreListener
import com.xadapter.setOnBind
import com.xadapter.setOnItemClickListener
import com.xadapter.setOnItemLongClickListener
import com.xadapter.setRefreshListener
import com.xadapter.setText

class ListUiFragment :
    ListFragment<ListPresenter, VideoListEntity, ListBaseEntity>(R.layout.ui_fragment_list) {

    companion object {
        fun newInstance(suffix: String, uiType: Int): ListUiFragment {
            val fragment = ListUiFragment()
            fragment.arguments = Bundle().apply {
                putString(BundleKey.suffix, suffix)
                putInt(BundleKey.uiType, uiType)
            }
            return fragment
        }
    }

    private val suffix by lazy { requireArguments().getString(BundleKey.suffix).toString() }
    private val uiType by lazy { requireArguments().getInt(BundleKey.uiType) }
    private val uiAdapter by lazy { mAdapter }
    private val uiPresenter by lazy { mPresenter }
    private val swipeRefreshLayout by lazy { requireView().findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout) }
    private val recyclerView by lazy { requireView().findViewById<RecyclerView>(R.id.recyclerView) }
    private val groupRoot by lazy { requireView().findViewById<View>(R.id.groupRoot) }
    private val chipGroup by lazy { requireView().findViewById<ChipGroup>(R.id.chipGroup) }

    override fun initPresenter(): ListPresenter = ListPresenterImpl(this)

    override fun swipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun recyclerView(): RecyclerView = recyclerView

    override fun initActivityCreated() {
        uiAdapter
            .setItemLayoutId(R.layout.ui_item_list)
            .setLoadMoreListener {
                uiPresenter.onNetWork(
                    page,
                    uiType,
                    ViewStatus.LOAD_MORE,
                    suffix
                )
            }
            .setRefreshListener {
                page = 0
                uiPresenter.onNetWork(page, uiType, ViewStatus.REFRESH, suffix)
            }
            .setOnBind { holder, _, entity ->
                holder.setText(R.id.ui_item_title, entity.title)
                holder.getImageView(R.id.ui_item_image).glide(entity.image)
            }
            .setOnItemLongClickListener { _, _, entity ->
                requireActivity().shareTextPlain(entity.title, "${entity.title}\n${entity.url}")
                true
            }
            .setOnItemClickListener { _, _, entity ->
                requireActivity().startActivity(DetailUiActivity::class.java, Bundle().apply {
                    putString(BundleKey.detailUri, entity.url)
                    putString(BundleKey.detailTitle, entity.title)
                    putString(BundleKey.detailImage, entity.image)
                })
            }
        onStatusRetry()
    }

    override fun onStatusRetry() {
        uiPresenter.onNetWork(page, uiType, ViewStatus.STATUS, suffix)
    }

    override fun onNetSuccess(entity: VideoListEntity) {
        val xTagEntity = entity.tagEntity
        val xListEntity = entity.listEntity
        if (page == 0 && xTagEntity.isNotEmpty()) {
            groupRoot.visibility = View.VISIBLE
            chipGroup.removeAllViews()
            xTagEntity.forEach {
                val chip = Chip(requireActivity())
                chip.text = it.title
                chip.setOnClickListener {
                    requireActivity().startActivity(
                        SearchListActivity::class.java,
                        Bundle().apply { putString(BundleKey.searchKey, chip.text.toString()) })
                }
                chipGroup.addView(chip)
            }
        }
        if (uiAdapter.dataContainer.containsAll(xListEntity)) {
            uiAdapter.loadNoMore()
            return
        }
        uiAdapter.addAll(xListEntity)
        onPagePlus()
    }
}



