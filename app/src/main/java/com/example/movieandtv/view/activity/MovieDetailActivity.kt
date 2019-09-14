package com.example.movieandtv.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.favorite_button_menu, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.favorite_action) {
            Toast.makeText(this, "Berhasi Favorite", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
