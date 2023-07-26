package fragment.develop.android.viewpager

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.RadioGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

class ViewPagerMainActivity : AppCompatActivity(R.layout.activity_view_pager),
    ViewPager.OnPageChangeListener,
    RadioGroup.OnCheckedChangeListener {

    private var ivWidth: Int = 0
    private val viewPager by lazy { findViewById<ViewPager>(R.id.viewPager) }
    private val rgGroup by lazy { findViewById<RadioGroup>(R.id.rgGroup) }
    private val ivLine by lazy { findViewById<View>(R.id.ivLine) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
