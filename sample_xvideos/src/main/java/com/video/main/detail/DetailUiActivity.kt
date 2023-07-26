package com.video.main.detail

import android.common.core.DetailView
import android.common.core.shareM3u8
import android.common.core.shareTextPlain
import android.common.core.startMxPlay
import android.common.ui.StatusActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.x.glide
import com.kotlin.x.startActivity
import com.video.main.BundleKey
import com.video.main.R
import com.video.main.net.Net
import com.video.main.net.PrevEntity
import com.video.main.net.UrlEntity
import com.video.main.net.VideoDetailEntity
import com.video.main.net.playUrl
import com.video.main.net.urlString
import com.xadapter.adapter.XAdapter
import com.xadapter.addAll
import com.xadapter.getImageView
import com.xadapter.setItemLayoutId
import com.xadapter.setOnBind
import com.xadapter.setOnItemClickListener
import com.xadapter.setOnItemLongClickListener
import com.xadapter.setText

class DetailUiActivity : StatusActivity<DetailPresenter>(R.layout.ui_activity_detail),
    DetailView<VideoDetailEntity> {

    private val url by lazy { intent.extras?.getString(BundleKey.detailUri).toString() }
    private val title by lazy { intent.extras?.getString(BundleKey.detailTitle).toString() }
    private val image by lazy { intent.extras?.getString(BundleKey.detailImage).toString() }
    private val mAdapter by lazy { XAdapter<PrevEntity>() }
    private val detailPrev by lazy { findViewById<RecyclerView>(R.id.detailPrev) }
    private val detailShare by lazy { findViewById<View>(R.id.detailShare) }
    private val detailPlay by lazy { findViewById<View>(R.id.detailPlay) }
    private val detailImage by lazy { findViewById<ImageView>(R.id.detailImage) }
    private val detailImageRoot by lazy { findViewById<View>(R.id.detailImageRoot) }

    private lateinit var urlEntity: UrlEntity

    override fun initCreate(savedInstanceState: Bundle?) {
        mToolbar.title = title
        detailPrev.layoutManager = GridLayoutManager(this, 2)
        detailPrev.setHasFixedSize(true)
        detailPrev.adapter = mAdapter
        mAdapter
            .setItemLayoutId(R.layout.ui_item_list)
            .setOnBind { holder, _, entity ->
                holder.setText(R.id.ui_item_title, entity.tf)
                holder.getImageView(R.id.ui_item_image).glide(entity.i)
            }
            .setOnItemClickListener { view, _, entity ->
                view.context.startActivity(DetailUiActivity::class.java, Bundle().apply {
                    putString(BundleKey.detailUri, Net.XVS_BASE + entity.u)
                    putString(BundleKey.detailTitle, entity.tf)
                    putString(BundleKey.detailImage, entity.i)
                })
            }
            .setOnItemLongClickListener { _, _, entity ->
                shareTextPlain(entity.tf, "${entity.tf}\n${Net.XVS_BASE + entity.u}")
                true
            }
        onStatusRetry()

        detailShare.setOnClickListener { shareM3u8(title, urlEntity.urlString()) }

        detailPlay.setOnClickListener {
            AlertDialog
                .Builder(this)
                .setTitle("请选择清晰度")
                .setSingleChoiceItems(
                    arrayOf("标清", "高清", "m3u8"),
                    View.NO_ID
                ) { dialog, which ->
                    startMxPlay(urlEntity.playUrl(which))
                    dialog.dismiss()
                }.show()
        }
    }

    override fun initPresenter(): DetailPresenter = DetailPresenterImpl(this)

    override fun onStatusRetry() {
        mPresenter.onNetWork(url)
    }

    override fun onNetSuccess(entity: VideoDetailEntity) {
        val prevEntity = entity.prevEntity
        val urlEntity = entity.urlEntity

        if (prevEntity.isNotEmpty()) {
            mAdapter.addAll(prevEntity)
        } else {
            detailPrev.visibility = View.GONE
        }
        val videos = urlEntity.playUrl(0)
        if (videos.isEmpty()) {
            detailImageRoot.visibility = View.GONE
            return
        }
        detailImage.glide(image.ifEmpty { urlEntity.thumbUrl169 })
        this.urlEntity = urlEntity
    }

    override fun hasStatusUI(): Boolean = true
}