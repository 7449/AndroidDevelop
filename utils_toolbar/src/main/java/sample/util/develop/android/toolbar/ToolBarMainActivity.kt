package sample.util.develop.android.toolbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ToolBarMainActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    private val appbar by lazy { findViewById<AppBarLayout>(R.id.appbar) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val right by lazy { findViewById<TextView>(R.id.right) }
    private val left by lazy { findViewById<TextView>(R.id.left) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.toolbar_activity_main)
        appbar.addOnOffsetChangedListener(this)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = MainAdapter()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        right.translationX =
            (abs(verticalOffset * 180) / appBarLayout.totalScrollRange).toFloat()
        right.translationY =
            (abs(verticalOffset * 30) / appBarLayout.totalScrollRange).toFloat()
        left.translationY =
            (-abs(verticalOffset * 40) / appBarLayout.totalScrollRange).toFloat()
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
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.toolbar_item_main, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mainTv.text = list[position]
        }

        override fun getItemCount(): Int {
            return list.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val mainTv = itemView.findViewById<TextView>(R.id.mainTv)
        }
    }
}
