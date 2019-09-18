package com.example.movieandtv.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.movieandtv.R
import com.example.movieandtv.database.FavMovieDatabase
import com.example.movieandtv.database.MovieModelDB
import com.example.movieandtv.model.MovieItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieData: MovieItem
    private val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/"
    private var favMovieDatabase: FavMovieDatabase? = null
    private var isFavorite: Boolean = false
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)


        Log.d("MovieDetail", "Already in Movie detail")

        val intent = intent
        movieData = intent.getParcelableExtra("MOVIE_DATA")
        movieData.posterPath.let {
            Picasso.get().load(BASE_URL_IMAGE + movieData.posterPath).into(img_detail_movie)
        }

        tv_title_detail_movie.text = movieData.title
        tv_rating_detail_movie.text = movieData.voteAverage.toString()
        tv_overview_detail_movie.text = movieData.overview
        favMovieDatabase = FavMovieDatabase.getInstance(this)
        favoriteCheck()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_button_menu, menu)
        this.menu = menu
        favoriteCheck()
        if (isFavorite) {
            menu?.getItem(0)?.setIcon(R.drawable.ic_favorite_white_24dp)
        } else {
            menu?.getItem(0)?.setIcon(R.drawable.ic_favorite_border_white_24dp)
        }
        super.onCreateOptionsMenu(menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.favorite_action) {
            if (isFavorite) {
                deleteFavorite(movieData)
                isFavorite = false
                setFavorite()
                Toast.makeText(this, "Success to Unfavorite", Toast.LENGTH_SHORT).show()
            } else {
                insertToDB(movieData)
                isFavorite = true
                setFavorite()
                Toast.makeText(this, "Berhasil Favorite", Toast.LENGTH_SHORT).show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun insertToDB(movieData: MovieItem) {
        val movieModelDB = MovieModelDB(
            movieData.overview,
            movieData.originalLanguage,
            movieData.originalTitle,
            movieData.title,
            movieData.posterPath,
            movieData.backdropPath,
            movieData.releaseDate,
            movieData.popularity,
            movieData.voteAverage,
            movieData.id,
            movieData.voteCount,
            "movie"
        )

        try {
            favMovieDatabase?.favMovieDao()?.insert(movieModelDB)
            Log.d("MovieDetail Insert", "Success to save")
        } catch (e: Exception) {
            Log.d("MovieDetail Insert", "Fail to save" + e.message)
        }

    }

    fun deleteFavorite(movieData: MovieItem) {
        val movieModelDB = MovieModelDB(
            movieData.overview,
            movieData.originalLanguage,
            movieData.originalTitle,
            movieData.title,
            movieData.posterPath,
            movieData.backdropPath,
            movieData.releaseDate,
            movieData.popularity,
            movieData.voteAverage,
            movieData.id,
            movieData.voteCount,
            "movie"
        )

        try {
            favMovieDatabase!!.favMovieDao().delete(movieModelDB)
            Toast.makeText(this, "success to unfavorite", Toast.LENGTH_SHORT).show()
            Log.d("MovieDetail favDelete", "Success to delete")
        } catch (e: Exception) {
            Log.d("MovieDetail favDelete", "Fail to delete " + e.message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FavMovieDatabase.destroyInstance()
    }

    fun setFavorite() {
        if (isFavorite) {
            menu?.getItem(0)?.setIcon(R.drawable.ic_favorite_white_24dp)
        } else {
            menu?.getItem(0)?.setIcon(R.drawable.ic_favorite_border_white_24dp)
        }
    }


    fun favoriteCheck() {
        val resultById = favMovieDatabase?.favMovieDao()?.getById(movieData.id!!)
        if (resultById?.id != null) {
            isFavorite = true
        }
    }
}
