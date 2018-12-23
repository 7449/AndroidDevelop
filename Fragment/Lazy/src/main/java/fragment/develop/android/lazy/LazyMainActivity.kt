package fragment.develop.android.lazy

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.lazy_activity_main.*
import java.util.*

class LazyMainActivity : AppCompatActivity() {

    private lateinit var mData: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lazy_activity_main)
        mData = ArrayList()
        mData.add("一")
        mData.add("二")
        mData.add("三")
        mData.add("四")
        mData.add("五")
        mData.add("六")
        mData.add("七")
        val tabNameAdapter = TabNameAdapter(supportFragmentManager)
        viewPager.adapter = tabNameAdapter
        viewPager.offscreenPageLimit = mData.size
        tabLayout.setupWithViewPager(viewPager)
    }

    inner class TabNameAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = TestLazyFragment.newInstance(position)
        override fun getPageTitle(position: Int): CharSequence? = mData[position]
        override fun getCount(): Int = mData.size
    }
}
