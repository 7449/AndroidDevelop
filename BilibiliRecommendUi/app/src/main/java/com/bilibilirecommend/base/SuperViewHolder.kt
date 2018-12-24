package com.bilibilirecommend.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.bannerlayout.widget.BannerLayout

class SuperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewSparseArray: SparseArray<View> = SparseArray()
    val context: Context = itemView.context

    init {
        itemView.tag = viewSparseArray
    }

    operator fun <T : View> get(id: Int): T {
        var childView: View? = viewSparseArray.get(id)
        if (childView == null) {
            childView = itemView.findViewById(id)
            viewSparseArray.put(id, childView)
        }
        return childView as T
    }

    fun getRecyclerView(id: Int): RecyclerView {
        return get(id)
    }

    fun getFrameLayout(id: Int): FrameLayout {
        return get(id)
    }

    fun getBannerLayout(id: Int): BannerLayout {
        return get(id)
    }

    fun getRelativeLayout(id: Int): RelativeLayout {
        return get(id)
    }

    fun getTextView(id: Int): TextView {
        return get(id)
    }

    fun getImageView(id: Int): ImageView {
        return get(id)
    }

    fun setTextView(id: Int, charSequence: CharSequence) {
        getTextView(id).text = charSequence
    }

    fun setTextColor(id: Int, color: Int) {
        getTextView(id).setTextColor(color)
    }

}
