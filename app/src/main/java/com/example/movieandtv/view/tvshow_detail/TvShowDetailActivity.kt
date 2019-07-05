package com.example.movieandtv.view.tvshow_detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.movieandtv.R
import com.example.movieandtv.model.TvShow
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tv_show_detail.*

class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var tvShow:TvShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_detail)

        var intent = intent
        tvShow = intent.getParcelableExtra("TVSHOW_DATA")
        tvShow.posterData.let {
            Picasso.get().load(tvShow.posterData).into(img_detail_tvshow)
        }
        tv_title_detail_tvshow.text = tvShow.titleData
        tv_genre_detail_tvshow.text = tvShow.genreData
        tv_episode_detail_tvshow.text = tvShow.episodeData
        tv_overview_detail_tvshow.text = tvShow.overviewData
    }
}
