package com.bilibilirecommend.main.widget.adapter


import android.support.v7.widget.StaggeredGridLayoutManager
import com.bannerlayout.model.BannerModel
import com.bilibilirecommend.R
import com.bilibilirecommend.base.BaseRecyclerAdapter
import com.bilibilirecommend.base.SuperViewHolder
import com.bilibilirecommend.main.model.RecommendCompat
import com.bilibilirecommend.main.model.RecommendModel
import com.bilibilirecommend.main.widget.holder.RecommendFooterViewFactory
import com.bilibilirecommend.main.widget.holder.RecommendHeaderHolder
import com.bilibilirecommend.main.widget.holder.RecommendItemViewHolder
import com.bilibilirecommend.network.Constant
import com.bilibilirecommend.utils.ImageLoaderUtils
import java.util.*


class RecommendAdapter : BaseRecyclerAdapter() {

    private lateinit var mDatas: RecommendModel
    private lateinit var banner: List<BannerModel>
    private val compats = ArrayList<RecommendCompat>()

    override fun getItemViewType(position: Int): Int {
        return compats[position].type
    }

    fun refreshData(model: RecommendModel) {
        this.mDatas = model
        notifyDataSetChanged()
    }

    override fun getLayoutId(viewType: Int): Int {
        return when (viewType) {
            RecommendCompat.TYPE_BANNER -> R.layout.item_recommend_banner
            RecommendCompat.TYPE_HEADER -> R.layout.item_recommend_header
            RecommendCompat.TYPE_FOOTER -> R.layout.item_recommend_footer
            RecommendCompat.TYPE_WEB_LINK -> R.layout.item_recommend_topic
            RecommendCompat.TYPE_ACTIVITY -> R.layout.item_recommend_activity
            else -> R.layout.item_recommend_item
        }
    }

    override fun onBindViewHolder(holder: SuperViewHolder, position: Int) {
        if (!::banner.isInitialized) {
            return
        }
        val newPosition = compats[position].newPosition
        when (compats[position].type) {
            RecommendCompat.TYPE_BANNER -> holder
                    .getBannerLayout(R.id.banner)
                    .initListResources(banner)
                    .initTips().start(true)
            RecommendCompat.TYPE_HEADER -> RecommendHeaderHolder(holder).setHeaderData(mDatas.result, newPosition)
            RecommendCompat.TYPE_FOOTER -> {
                val footView = holder.getFrameLayout(R.id.fl_recommend_footer)
                footView.removeAllViews()
                footView.addView(RecommendFooterViewFactory.createView(mDatas.result[newPosition]))
            }
            RecommendCompat.TYPE_ITEM -> RecommendItemViewHolder(holder).setData(mDatas.result[newPosition].body[compats[position].itemPosition], mDatas.result[newPosition].type)
            RecommendCompat.TYPE_ACTIVITY -> {
                val recyclerView = holder.getRecyclerView(R.id.recyclerView)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
                recyclerView.adapter = RecommendActivityAdapter(mDatas.result[newPosition].body)
            }
            RecommendCompat.TYPE_WEB_LINK -> ImageLoaderUtils.display(holder.getImageView(R.id.iv_topic), mDatas.result[newPosition].body[0].cover)
        }
    }


    override fun getItemCount(): Int {
        var temp = 0
        if (!::mDatas.isInitialized) {
            return temp
        }
        compats.clear()
        temp += 1
        compats.add(RecommendCompat(temp, RecommendCompat.TYPE_BANNER, -1))
        val result = mDatas.result
        for (i in result.indices) {
            when (result[i].type) {
                Constant.TYPE_RECOMMEND, Constant.TYPE_LIVE, Constant.TYPE_BANGUMI, Constant.TYPE_REGION -> {
                    temp += 1
                    compats.add(RecommendCompat(temp, RecommendCompat.TYPE_HEADER, i))
                    for (j in 0 until result[i].body.size) {
                        temp += 1
                        compats.add(RecommendCompat(temp, RecommendCompat.TYPE_ITEM, i, j))
                    }
                    temp += 1
                    compats.add(RecommendCompat(temp, RecommendCompat.TYPE_FOOTER, i))
                }
                Constant.TYPE_WEB_LINK -> {
                    temp += 1
                    compats.add(RecommendCompat(temp, RecommendCompat.TYPE_HEADER, i))
                    temp += 1
                    compats.add(RecommendCompat(temp, RecommendCompat.TYPE_WEB_LINK, i))
                }
                Constant.TYPE_ACTIVITY_ -> {
                    temp += 1
                    compats.add(RecommendCompat(temp, RecommendCompat.TYPE_HEADER, i))
                    temp += 1
                    compats.add(RecommendCompat(temp, RecommendCompat.TYPE_ACTIVITY, i))
                }
            }
        }
        return temp
    }

    fun setBannerData(data: List<BannerModel>) {
        this.banner = data
    }
}
