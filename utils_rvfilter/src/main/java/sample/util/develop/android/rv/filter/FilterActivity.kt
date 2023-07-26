package sample.util.develop.android.rv.filter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sample.util.develop.android.rv.filter.widget.RVUtils
import sample.util.develop.android.rv.filter.widget.filter.FilterAdapter

class FilterActivity : AppCompatActivity(), View.OnClickListener {

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val ok by lazy { findViewById<View>(R.id.ok) }
    private val tv by lazy { findViewById<TextView>(R.id.tv) }

    private lateinit var filterAdapter: FilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_activity_filter)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 3)
        filterAdapter = FilterAdapter(RVUtils.initFilterData())
        recyclerView.adapter = filterAdapter
        ok.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val selectBean = filterAdapter.selectBean
        tv.text = selectBean.toString()
    }
}
