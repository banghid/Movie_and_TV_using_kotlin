package com.example.movieandtv.view.movie_detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.movieandtv.R
import com.example.movieandtv.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieData: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val intent = intent
        movieData = intent.getParcelableExtra("MOVIE_DATA")
        movieData.posterData.let {
            Picasso.get().load(movieData.posterData).into(img_detail_movie)
        }

        tv_title_detail_movie.text = movieData.titleData
        tv_genre_detail_movie.text = movieData.genreData
        tv_overview_detail_movie.text = movieData.overviewData
    }
}
