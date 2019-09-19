package com.example.movieandtv.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.movieandtv.R

class MyPagerAdapter(
    fm: FragmentManager,
    private val context: Context,
    private val pages: List<Fragment>
) : FragmentPagerAdapter(fm) {

//    private val pages = listOf(
//        MovieFragment(),
//        TvshowFragment()
//    )

    override fun getCount(): Int {
        return pages.size
    }

    override fun getItem(p0: Int): Fragment {
        return pages[p0]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> context.resources.getString(R.string.tab_movie)
            else -> context.resources.getString(R.string.tab_tv)
        }
    }
}