package sample.util.develop.android.dagger.mvp.widget

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dagger_item.view.*
import sample.util.develop.android.dagger.R
import sample.util.develop.android.dagger.mvp.model.MVPBean

/**
 * by y on 2017/5/31.
 */

internal class MVPAdapter(private val list: MutableList<MVPBean>) : RecyclerView.Adapter<MVPAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dagger_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val simpleModel = list[position]
        Glide
            .with(holder.itemView.listImage.context)
            .load(simpleModel.titleImage)
            .into(holder.itemView.listImage)
        holder.itemView.listTv.text = simpleModel.title
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addAll(list: List<MVPBean>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}
