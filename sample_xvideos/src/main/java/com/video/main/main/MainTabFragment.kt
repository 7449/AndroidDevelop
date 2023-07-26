package com.video.main.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.video.main.BundleKey
import com.video.main.R

class MainTabFragment : Fragment() {

    companion object {
        fun newInstance(type: Int): MainTabFragment {
            val tabFragment = MainTabFragment()
            val bundle = Bundle()
            bundle.putInt(BundleKey.uiType, type)
            tabFragment.arguments = bundle
            return tabFragment
        }
    }

    private val type by lazy { requireArguments().getInt(BundleKey.uiType) }
    private val viewPager by lazy { requireView().findViewById<ViewPager>(R.id.viewPager) }
    private val tabLayout by lazy { requireView().findViewById<TabLayout>(R.id.tab_layout) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabAdapter = MainTabAdapter(childFragmentManager, requireActivity(), type)
        viewPager.adapter = tabAdapter
        tabLayout.setupWithViewPager(viewPager)
        viewPager.offscreenPageLimit = tabAdapter.count
    }

}