package com.example.movieandtv.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.movieandtv.R
import com.example.movieandtv.model.TvShowItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tv_show_detail.*

class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var tvShow: TvShowItem
    private val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        val intent = intent
        tvShow = intent.getParcelableExtra("TVSHOW_DATA")
        tvShow.posterPath.let {
            Picasso.get().load(BASE_URL_IMAGE + tvShow.posterPath).into(img_detail_tvshow)
        }
        tv_title_detail_tvshow.text = tvShow.name
        tv_rating_detail_tvshow.text = tvShow.voteAverage.toString()
        tv_overview_detail_tvshow.text = tvShow.overview
    }
}
