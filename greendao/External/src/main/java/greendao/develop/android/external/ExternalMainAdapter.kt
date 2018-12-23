package greendao.develop.android.external

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.external_item.view.*

class ExternalMainAdapter(private val list: List<ExternalBean>) :
    RecyclerView.Adapter<ExternalMainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.external_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvId.text = list[position].id.toString()
        holder.itemView.tvEmail.text = list[position].email.toString()
        holder.itemView.setOnClickListener { }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
