package com.video.main.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.video.main.R
import com.video.main.net.VideoUiType
import com.video.main.net.cancelRequestAll

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    MainView {

    private val mainPresenterImpl by lazy { MainPresenterImpl(this) }
    private val navigation by lazy { findViewById<NavigationView>(R.id.navigation) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val mDrawerLayout by lazy { findViewById<DrawerLayout>(R.id.mDrawerLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setNavigationItemSelectedListener(this)
        mainPresenterImpl.switchId(VideoUiType.TAGS)
        toolbar.title = getString(R.string.stockings_title)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        toolbar.setNavigationOnClickListener { mDrawerLayout.openDrawer(GravityCompat.START) }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        toolbar.title = item.title
        when (item.itemId) {
            R.id.lang -> mainPresenterImpl.switchId(VideoUiType.LANG)
            R.id.tags -> mainPresenterImpl.switchId(VideoUiType.TAGS)
        }
        mDrawerLayout.closeDrawers()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.open_search -> mainPresenterImpl.onSearch()
        }
        return super.onOptionsItemSelected(item)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        cancelRequestAll()
        super.onDestroy()
    }

    override val mainActivity: AppCompatActivity = this

}





