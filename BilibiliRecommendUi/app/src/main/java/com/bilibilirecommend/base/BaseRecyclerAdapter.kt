package com.bilibilirecommend.base

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bilibilirecommend.main.model.RecommendCompat


abstract class BaseRecyclerAdapter : RecyclerView.Adapter<SuperViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperViewHolder {
        return SuperViewHolder(LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false))
    }

    protected abstract fun getLayoutId(viewType: Int): Int

    override fun onViewAttachedToWindow(holder: SuperViewHolder) {
        super.onViewAttachedToWindow(holder)
        val layoutParams = holder.itemView.layoutParams
        if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            layoutParams.isFullSpan = getItemViewType(holder.layoutPosition) != RecommendCompat.TYPE_ITEM
        }
    }
}