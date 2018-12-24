package sample.view.develop.android.refresh

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.refresh_activity_main.*
import sample.view.develop.android.refresh.refresh.PullToRefreshView
import sample.view.develop.android.refresh.refresh.PullToRefreshView.STATUS_REFRESH_SUCCESS

class RefreshMainActivity : AppCompatActivity(), PullToRefreshView.OnRefreshListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.refresh_activity_main)
        pull_view.setOnRefreshListener(this)

        tv.setOnClickListener { onRefresh() }
    }

    override fun onRefresh() {
        pull_view.changeStatus(PullToRefreshView.STATUS_REFRESHING)
        Handler().postDelayed({
            pull_view.changeStatus(STATUS_REFRESH_SUCCESS)
            Toast.makeText(applicationContext, "刷新成功", Toast.LENGTH_SHORT).show()
        }, 1000)
    }
}
