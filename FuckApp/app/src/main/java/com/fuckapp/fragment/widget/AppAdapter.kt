package com.fuckapp.fragment.widget

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fuckapp.R
import com.fuckapp.fragment.model.AppModel
import com.fuckapp.utils.Constant
import kotlinx.android.synthetic.main.app_item.view.*
import java.util.*

/**
 * by y on 2016/10/31
 */

class AppAdapter(var type: Int) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    private val tempApp = ArrayList<AppModel>()
    private var onItemClickListener: OnItemClickListener? = null
    private val appModel = ArrayList<AppModel>()
    private val booleanArray = SparseBooleanArray()

    val tempList: List<AppModel>
        get() = tempApp


    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.app_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (type != -1) {
            when (type) {
                Constant.HIDE_APP -> holder.itemView.app_check.visibility = View.GONE
                else -> holder.itemView.app_check.visibility = View.VISIBLE
            }
        }
        holder.itemView.imgApp.setImageDrawable(appModel[position].appIcon)
        holder.itemView.tvAppLabel.text = "程序名：" + appModel[position].appLabel
        holder.itemView.tvPkgName.text = "包名：" + appModel[position].pkgName
        if (booleanArray.get(position)) {
            holder.itemView.app_check.isChecked = true
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorGray))
        } else {
            holder.itemView.app_check.isChecked = false
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorWhite))
        }
        holder.itemView.setOnClickListener { onItemClickListener?.onItemClick(position, appModel[position]) }
    }

    fun refreshUI(list: List<AppModel>?) {
        if (list != null) {
            appModel.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun removeAll() {
        appModel.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return appModel.size
    }

    fun refreshCheckBox(position: Int) {
        if (booleanArray.get(position)) {
            booleanArray.put(position, false)
            tempApp.remove(appModel[position])
        } else {
            booleanArray.put(position, true)
            tempApp.add(appModel[position])
        }
        notifyDataSetChanged()
    }

    fun resetCheckbox() {
        booleanArray.clear()
        clearTempList()
        notifyDataSetChanged()
    }

    fun clearTempList() {
        tempApp.clear()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClick(position: Int, appInfo: AppModel)
    }
}

