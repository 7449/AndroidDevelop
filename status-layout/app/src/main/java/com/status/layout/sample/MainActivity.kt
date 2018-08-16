package com.status.layout.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.status.layout.SimpleOnStatusClickListener
import com.status.layout.Status
import com.status.layout.StatusLayout


class MainActivity : AppCompatActivity() {

    private lateinit var statusLayout: StatusLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        statusLayout = findViewById(R.id.status_root_view)
        statusLayout.setOnStatusClickListener(object : SimpleOnStatusClickListener() {
            override fun onEmptyClick(view: View) {
                super.onEmptyClick(view)
            }

            override fun onErrorClick(view: View) {
                super.onErrorClick(view)
            }
        })
        val b = statusLayout.setStatus(Status.LOADING)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.status_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        statusLayout.setStatus(item.title.toString())
        return true
    }

}
