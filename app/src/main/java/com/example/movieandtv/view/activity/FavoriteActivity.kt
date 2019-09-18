package com.example.movieandtv.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.movieandtv.R
import com.example.movieandtv.adapter.FavoriteAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        favorite_pager.adapter = FavoriteAdapter(supportFragmentManager, this)
        favorite_tabs.setupWithViewPager(favorite_pager)
        supportActionBar?.title = resources.getString(R.string.favorite_title)
    }
}
