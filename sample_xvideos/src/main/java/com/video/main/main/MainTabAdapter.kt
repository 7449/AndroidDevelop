package com.video.main.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kotlin.x.getStringArray
import com.video.main.R
import com.video.main.list.ListUiFragment
import com.video.main.net.VideoUiType

class MainTabAdapter(fm: FragmentManager, context: Context, private val uiType: Int) :
    FragmentPagerAdapter(fm) {

    private val array = when (uiType) {
        VideoUiType.TAGS -> context.getStringArray(R.array.tags_tabs)
        else -> context.getStringArray(R.array.lang_tabs)
    }

    override fun getItem(position: Int): Fragment =
        ListUiFragment.newInstance(array[position], uiType)

    override fun getPageTitle(position: Int): CharSequence = array[position]
    override fun getCount(): Int = array.size
}
