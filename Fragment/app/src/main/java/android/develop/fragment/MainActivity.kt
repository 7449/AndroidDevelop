package android.develop.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fragment.develop.android.lazy.LazyMainActivity
import fragment.develop.android.tab.TabMainActivity
import fragment.develop.android.viewpager.ViewPagerMainActivity
import fragment.develop.android.viewpager.two.ViewPager2MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabFragment.setOnClickListener { startActivity(Intent(this, TabMainActivity::class.java)) }
        lazyFragment.setOnClickListener { startActivity(Intent(this, LazyMainActivity::class.java)) }
        viewPagerFragment.setOnClickListener { startActivity(Intent(this, ViewPagerMainActivity::class.java)) }
        viewPagerFragment2.setOnClickListener { startActivity(Intent(this, ViewPager2MainActivity::class.java)) }
    }
}
