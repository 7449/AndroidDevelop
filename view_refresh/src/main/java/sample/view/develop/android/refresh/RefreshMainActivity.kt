package sample.view.develop.android.refresh

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import sample.view.develop.android.refresh.refresh.PullToRefreshView
import sample.view.develop.android.refresh.refresh.PullToRefreshView.STATUS_REFRESH_SUCCESS

class RefreshMainActivity : AppCompatActivity(), PullToRefreshView.OnRefreshListener {

    private val refreshView by lazy { findViewById<PullToRefreshView>(R.id.pull_view) }
    private val tv by lazy { findViewById<TextView>(R.id.tv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.refresh_activity_main)
        refreshView.setOnRefreshListener(this)
        tv.setOnClickListener { onRefresh() }
    }

    override fun onRefresh() {
        refreshView.changeStatus(PullToRefreshView.STATUS_REFRESHING)
        Handler(Looper.getMainLooper()).postDelayed({
            refreshView.changeStatus(STATUS_REFRESH_SUCCESS)
            Toast.makeText(applicationContext, "刷新成功", Toast.LENGTH_SHORT).show()
        }, 1000)
    }

}
