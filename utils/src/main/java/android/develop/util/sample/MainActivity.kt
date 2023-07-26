package android.develop.util.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
        findViewById<View>(R.id.error).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ErrorMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.finger).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    FingerMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.linkTop).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    LinkTopMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.splash).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SplashMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.js).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    JsMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.imageSelect).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ImageSelectMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.expandableList).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ExpandableListMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.statusBar).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    StatusBarMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.saveImage).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SaveImageMainActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.rvFilter).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    FilterActivity::class.java
                )
            )
        }
        findViewById<View>(R.id.toolbar).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ToolBarMainActivity::class.java
                )
            )
        }
    }
}
