package sample.util.develop.android.toolbar

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.toolbar_activity_main.*
import kotlinx.android.synthetic.main.toolbar_item_main.view.*
import java.util.*

class ToolBarMainActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toolbar_activity_main)
        appbar.addOnOffsetChangedListener(this)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = MainAdapter()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        right.translationX = (Math.abs(verticalOffset * 180) / appBarLayout.totalScrollRange).toFloat()
        right.translationY = (Math.abs(verticalOffset * 30) / appBarLayout.totalScrollRange).toFloat()
        left.translationY = (-Math.abs(verticalOffset * 40) / appBarLayout.totalScrollRange).toFloat()
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {
    }

    private class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

        private var list: MutableList<String> = ArrayList()

        init {
            for (i in 0..39) {
                list.add("item  + $i")
            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.toolbar_item_main, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.mainTv.text = list[position]
        }

        override fun getItemCount(): Int {
            return list.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}
