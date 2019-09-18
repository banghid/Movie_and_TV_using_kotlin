package com.example.movieandtv.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.movieandtv.R
import com.example.movieandtv.view.fragment.FavoriteMovieFragment
import com.example.movieandtv.view.fragment.FavoriteTvShowFragment


class FavoriteAdapter(fm: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fm) {

    private val pages = listOf<Fragment>(
        FavoriteMovieFragment(),
        FavoriteTvShowFragment()
    )

    override fun getItem(p0: Int): Fragment {
        return pages[p0]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when (position) {
            0 -> "Favorite " + context.resources.getString(R.string.tab_movie)
            else -> "Favorite " + context.resources.getString(R.string.tab_tv)
        }
    }
}