package sample.util.develop.android.rv.filter


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.rv_activity_filter.*
import sample.util.develop.android.rv.filter.widget.RVUtils
import sample.util.develop.android.rv.filter.widget.filter.FilterAdapter

class FilterActivity : AppCompatActivity(), View.OnClickListener {


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
