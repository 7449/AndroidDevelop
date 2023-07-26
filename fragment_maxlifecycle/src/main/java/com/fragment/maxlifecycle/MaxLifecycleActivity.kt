package com.fragment.maxlifecycle

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MaxLifecycleActivity : AppCompatActivity() {

    private val viewPager by lazy { findViewById<ViewPager>(R.id.viewPager) }
    private val tabLayout by lazy { findViewById<TabLayout>(R.id.tabLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_max_lifecycle)
        viewPager.adapter = TabAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit = array.size
    }

}

val array = arrayOf("预览", "会见", "利己", "XX")

class TabAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = SampleFragment().newInstance(array[position])
    override fun getPageTitle(position: Int): CharSequence = array[position]
    override fun getCount(): Int = array.size
}

fun SampleFragment.newInstance(text: String) = also { message = text }
fun logE(text: String) = Log.e("kotlin", text)

class SampleFragment : LazyFragment() {
    lateinit var message: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_max_lifecycle, container, false)

    override fun lazyLoading() {
        logE("lazyLoading:$message")
        Handler(Looper.getMainLooper()).postDelayed({
            requireView().findViewById<ProgressBar>(R.id.progress).visibility = View.GONE
            requireView().findViewById<AppCompatTextView>(R.id.text).text = message
            requireView().findViewById<AppCompatTextView>(R.id.text).visibility = View.VISIBLE
        }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logE("onCreate:$message")
    }

    override fun onResume() {
        super.onResume()
        logE("onResume:$message")
    }

    override fun onStart() {
        super.onStart()
        logE("onStart:$message")
    }

    override fun onDestroy() {
        super.onDestroy()
        logE("onDestroy:$message")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        logE("onDestroyView:$message")
    }

}

abstract class LazyFragment : Fragment() {

    var lazy = false

    override fun onResume() {
        super.onResume()
        if (!lazy) {
            lazy = true
            lazyLoading()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lazy = false
    }

    protected abstract fun lazyLoading()

}