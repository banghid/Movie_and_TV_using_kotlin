package com.example.movieandtv.view.movie_detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.movieandtv.R
import com.example.movieandtv.model.MovieItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieData: MovieItem
    private val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val intent = intent
        movieData = intent.getParcelableExtra("MOVIE_DATA")
        movieData.posterPath.let {
            Picasso.get().load(BASE_URL_IMAGE + movieData.posterPath).into(img_detail_movie)
        }

        tv_title_detail_movie.text = movieData.title
        tv_rating_detail_movie.text = movieData.voteAverage.toString()
        tv_overview_detail_movie.text = movieData.overview
    }
}
