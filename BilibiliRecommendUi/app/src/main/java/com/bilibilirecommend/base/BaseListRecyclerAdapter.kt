package com.bilibilirecommend.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseListRecyclerAdapter<T>(private var mDatas: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val dataCount: Int = mDatas.size

    protected abstract val layoutId: Int

    fun getView(viewGroup: ViewGroup, id: Int): View {
        return LayoutInflater.from(viewGroup.context).inflate(id, viewGroup, false)
    }

    fun addAll(datas: List<T>) {
        mDatas.addAll(datas)
        this.notifyDataSetChanged()
    }

    fun remove(position: Int) {
        mDatas.removeAt(position)
        this.notifyDataSetChanged()
    }

    fun removeAll() {
        if (mDatas.size != 0) {
            mDatas.clear()
        }
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SuperViewHolder(getView(parent, layoutId))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBind(holder as SuperViewHolder, position, mDatas[position])
    }

    protected abstract fun onBind(viewHolder: SuperViewHolder, position: Int, mDatas: T)

    override fun getItemCount(): Int {
        return mDatas.size
    }

}