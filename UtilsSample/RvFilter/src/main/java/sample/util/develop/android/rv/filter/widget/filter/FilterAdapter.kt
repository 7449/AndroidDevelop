package sample.util.develop.android.rv.filter.widget.filter

import android.annotation.SuppressLint
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.ViewGroup
import sample.util.develop.android.rv.filter.R
import sample.util.develop.android.rv.filter.widget.RVHolder
import sample.util.develop.android.rv.filter.widget.RVUtils
import java.util.*

class FilterAdapter(private val list: List<FilterBean>) : RecyclerView.Adapter<RVHolder>() {

    companion object {
        const val TYPE_LINE = 0
        const val TYPE_TITLE = 1
        const val TYPE_ITEM = 2
        const val FESTIVAL = "节日"
        const val EMISSION = "排放"
        const val WEEK = "星期"
        const val COLOR = "颜色"
        const val YEARS = "年份"
        const val MONTH = "月份"
    }

    val selectBean = LinkedHashMap<String, String>()

    init {
        for (filterBean in list) {
            if (filterBean.isSelect) {
                selectBean[filterBean.titleType] = filterBean.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        return when (viewType) {
            TYPE_LINE -> RVHolder(RVUtils.getView(parent, R.layout.item_filter_line))
            TYPE_TITLE -> RVHolder(RVUtils.getView(parent, R.layout.item_filter_title))
            else -> RVHolder(RVUtils.getView(parent, R.layout.item_filter_item))
        }
    }

    override fun onBindViewHolder(holder: RVHolder, @SuppressLint("RecyclerView") position: Int) {
        val filterBean = list[position]
        when (getItemViewType(position)) {
            TYPE_ITEM -> {

                val tv = holder.get<AppCompatTextView>(R.id.filterTvContent)
                tv.text = filterBean.content

                tv.isSelected = TextUtils.equals(list[position].content, selectBean[filterBean.titleType])

                tv.setOnClickListener { v ->
                    if (!v.isSelected) {
                        selectBean[filterBean.titleType] = filterBean.content
                    }
                    notifyDataSetChanged()
                }
            }
            TYPE_TITLE -> holder.setTextView(R.id.filterTvTitle, filterBean.titleType)
            else -> {
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (getItemViewType(position) != TYPE_ITEM) {
                        manager.spanCount
                    } else {
                        1
                    }
                }
            }
        }
    }
}
