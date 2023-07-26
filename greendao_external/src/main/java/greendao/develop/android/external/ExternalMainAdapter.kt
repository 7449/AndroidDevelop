package greendao.develop.android.external

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExternalMainAdapter(private val list: List<ExternalBean>) :
    RecyclerView.Adapter<ExternalMainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.external_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvId.text = list[position].id.toString()
        holder.tvEmail.text = list[position].email.toString()
        holder.itemView.setOnClickListener { }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvId = itemView.findViewById<TextView>(R.id.tvId)
        val tvEmail = itemView.findViewById<TextView>(R.id.tvEmail)
    }
}
