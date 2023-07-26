package objectbox.develop.android.two

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import objectbox.develop.android.entity.ObjectBox2xEntity

class Version2xAdapter(private var list: MutableList<ObjectBox2xEntity>) :
    RecyclerView.Adapter<Version2xAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.two_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val objectBoxEntity = list[position]
        holder.name.text = "姓名:${objectBoxEntity.name}"
        holder.age.text = "年龄:${objectBoxEntity.age}"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addAll(list: MutableList<ObjectBox2xEntity>) {
        if (this.list.isEmpty()) {
            this.list = list
        } else {
            this.list.clear()
            this.list.addAll(list)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
        val age = itemView.findViewById<TextView>(R.id.age)
    }
}