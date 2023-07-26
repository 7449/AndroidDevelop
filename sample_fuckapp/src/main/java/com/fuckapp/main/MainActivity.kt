package com.fuckapp.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.fuckapp.R
import com.fuckapp.fragment.widget.AppFragment
import com.fuckapp.utils.Constant
import com.fuckapp.utils.SPUtils
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val navigationview by lazy { findViewById<NavigationView>(R.id.navigationview) }
    private val dlLayout by lazy { findViewById<DrawerLayout>(R.id.dlLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!SPUtils.getBoolean(SPUtils.WARN_APP)) {
            AlertDialog.Builder(this)
                .setMessage("手机需要用root权限")
                .setPositiveButton("ok", null).create().show()
            SPUtils.setBoolean(SPUtils.WARN_APP, true)
        }
        setContentView(R.layout.activity_main)
        toolbar.title = getString(R.string.all_app)
        navigationview.setNavigationItemSelectedListener(this)
        replaceFragment(Constant.ALL_APP)
        setSupportActionBar(toolbar)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        toolbar.title = item.title
        when (item.itemId) {
            R.id.all_app -> replaceFragment(Constant.ALL_APP)
            R.id.system_app -> replaceFragment(Constant.SYSTEM_APP)
            R.id.no_system_app -> replaceFragment(Constant.NO_SYSTEM_APP)
            R.id.hide_app -> replaceFragment(Constant.HIDE_APP)
        }
        dlLayout.closeDrawers()
        return true
    }

    private fun replaceFragment(type: Int) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, AppFragment.start(type))
            .commit()
    }

}
