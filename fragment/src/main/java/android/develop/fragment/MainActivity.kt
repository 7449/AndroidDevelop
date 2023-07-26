package android.develop.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.fragment.maxlifecycle.MaxLifecycleActivity
import fragment.develop.android.lazy.LazyMainActivity
import fragment.develop.android.tab.TabMainActivity
import fragment.develop.android.viewpager.ViewPagerMainActivity
import fragment.develop.android.viewpager.two.ViewPager2MainActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.tabFragment).setOnClickListener {
            startActivity(Intent(this, TabMainActivity::class.java))
        }
        findViewById<View>(R.id.lazyFragment).setOnClickListener {
            startActivity(Intent(this, LazyMainActivity::class.java))
        }
        findViewById<View>(R.id.viewPagerFragment).setOnClickListener {
            startActivity(Intent(this, ViewPagerMainActivity::class.java))
        }
        findViewById<View>(R.id.viewPagerFragment2).setOnClickListener {
            startActivity(Intent(this, ViewPager2MainActivity::class.java))
        }
        findViewById<View>(R.id.max_lifecycle).setOnClickListener {
            startActivity(Intent(this, MaxLifecycleActivity::class.java))
        }
    }

}
