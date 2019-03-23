package fragment.develop.android.viewpager.two

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.widget.RadioGroup
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_view_pager2.*

class ViewPager2MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    private var ivWidth: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_view_pager2)
        val display = window.windowManager.defaultDisplay
        val dm = DisplayMetrics()
        display.getMetrics(dm)
        ivWidth = dm.widthPixels / 4
        viewPager.addOnPageChangeListener(this)
        rgGroup.setOnCheckedChangeListener(this)
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int): Fragment {
                return ViewPagerMainFragment.startFragment(position)
            }

            override fun getCount(): Int {
                return 4
            }
        }
        viewPager.currentItem = 4
    }

    fun currentItem() {
        viewPager.setCurrentItem(viewPager.currentItem - 1, true)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val lp = ivLine.layoutParams as RelativeLayout.LayoutParams
        lp.leftMargin = position * ivWidth + (positionOffset * ivWidth).toInt()
        lp.width = ivWidth
        ivLine.layoutParams = lp
    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onCheckedChanged(radioGroup: RadioGroup, i: Int) {
        when (i) {
            R.id.rb_one -> viewPager.currentItem = 0
            R.id.rb_two -> viewPager.currentItem = 1
            R.id.rb_three -> viewPager.currentItem = 2
            R.id.rb_four -> viewPager.currentItem = 3
        }
    }
}
