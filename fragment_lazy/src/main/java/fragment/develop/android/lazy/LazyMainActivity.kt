package fragment.develop.android.lazy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class LazyMainActivity : AppCompatActivity(R.layout.lazy_activity_main) {

    private val mData = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lazy_activity_main)
        mData.add("一")
        mData.add("二")
        mData.add("三")
        mData.add("四")
        mData.add("五")
        mData.add("六")
        mData.add("七")
        val tabNameAdapter = TabNameAdapter(supportFragmentManager)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager.adapter = tabNameAdapter
        viewPager.offscreenPageLimit = mData.size
        tabLayout.setupWithViewPager(viewPager)
    }

    inner class TabNameAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = TestLazyFragment.newInstance(position)
        override fun getPageTitle(position: Int): CharSequence = mData[position]
        override fun getCount(): Int = mData.size
    }
}
