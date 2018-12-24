package android.develop.util.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import sample.util.develop.android.error.ErrorMainActivity
import sample.util.develop.android.expandablelist.ExpandableListMainActivity
import sample.util.develop.android.finger.FingerMainActivity
import sample.util.develop.android.image.select.ImageSelectMainActivity
import sample.util.develop.android.js.JsMainActivity
import sample.util.develop.android.linktop.LinkTopMainActivity
import sample.util.develop.android.rv.filter.FilterActivity
import sample.util.develop.android.save.image.SaveImageMainActivity
import sample.util.develop.android.splash.SplashMainActivity
import sample.util.develop.android.statusbar.StatusBarMainActivity
import sample.util.develop.android.toolbar.ToolBarMainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        error.setOnClickListener { startActivity(Intent(this, ErrorMainActivity::class.java)) }
        finger.setOnClickListener { startActivity(Intent(this, FingerMainActivity::class.java)) }
        linkTop.setOnClickListener { startActivity(Intent(this, LinkTopMainActivity::class.java)) }
        splash.setOnClickListener { startActivity(Intent(this, SplashMainActivity::class.java)) }
        js.setOnClickListener { startActivity(Intent(this, JsMainActivity::class.java)) }
        imageSelect.setOnClickListener { startActivity(Intent(this, ImageSelectMainActivity::class.java)) }
        expandableList.setOnClickListener { startActivity(Intent(this, ExpandableListMainActivity::class.java)) }
        statusBar.setOnClickListener { startActivity(Intent(this, StatusBarMainActivity::class.java)) }
        saveImage.setOnClickListener { startActivity(Intent(this, SaveImageMainActivity::class.java)) }
        rvFilter.setOnClickListener { startActivity(Intent(this, FilterActivity::class.java)) }
        toolbar.setOnClickListener { startActivity(Intent(this, ToolBarMainActivity::class.java)) }
    }
}
