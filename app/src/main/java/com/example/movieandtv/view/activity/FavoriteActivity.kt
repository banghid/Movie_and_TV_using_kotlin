package com.example.movieandtv.view.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.movieandtv.R
import com.example.movieandtv.adapter.MyPagerAdapter
import com.example.movieandtv.view.fragment.FavoriteMovieFragment
import com.example.movieandtv.view.fragment.FavoriteTvShowFragment
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val pages = listOf<Fragment>(
            FavoriteMovieFragment(),
            FavoriteTvShowFragment()
        )
        favorite_pager.adapter = MyPagerAdapter(supportFragmentManager, this, pages)
        favorite_tabs.setupWithViewPager(favorite_pager)
        supportActionBar?.title = resources.getString(R.string.favorite_title)
    }
}
