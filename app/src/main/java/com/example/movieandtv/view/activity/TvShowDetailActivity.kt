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
import com.example.movieandtv.model.TvShowItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tv_show_detail.*

class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var tvShow: TvShowItem
    private val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/"
    private var favMovieDatabase: FavMovieDatabase? = null
    private var isFavorite: Boolean = false
    private var menu: Menu? = null

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
        supportActionBar?.title = resources.getString(R.string.tvshow_detail_title)
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
                deleteFavorite(tvShow)
                isFavorite = false
                setFavorite()
                Toast.makeText(this, "Success to Unfavorite", Toast.LENGTH_SHORT).show()
            } else {
                insertToDB(tvShow)
                isFavorite = true
                setFavorite()
                Toast.makeText(this, "Berhasil Favorite", Toast.LENGTH_SHORT).show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertToDB(tvShow: TvShowItem) {
        val movieModelDB = MovieModelDB(
            tvShow.overview,
            tvShow.originalLanguage,
            tvShow.originalName,
            tvShow.name,
            tvShow.posterPath,
            tvShow.backdropPath,
            tvShow.firstAirDate,
            tvShow.popularity,
            tvShow.voteAverage,
            tvShow.id,
            tvShow.voteCount,
            "tv_show"
        )

        try {
            favMovieDatabase?.favMovieDao()?.insert(movieModelDB)
            Log.d("MovieDetail Insert", "Success to save")
        } catch (e: Exception) {
            Log.d("MovieDetail Insert", "Fail to save" + e.message)
        }

    }

    private fun deleteFavorite(tvShow: TvShowItem) {
        val movieModelDB = MovieModelDB(
            tvShow.overview,
            tvShow.originalLanguage,
            tvShow.originalName,
            tvShow.name,
            tvShow.posterPath,
            tvShow.backdropPath,
            tvShow.firstAirDate,
            tvShow.popularity,
            tvShow.voteAverage,
            tvShow.id,
            tvShow.voteCount,
            "tv_show"
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

    private fun setFavorite() {
        if (isFavorite) {
            menu?.getItem(0)?.setIcon(R.drawable.ic_favorite_white_24dp)
        } else {
            menu?.getItem(0)?.setIcon(R.drawable.ic_favorite_border_white_24dp)
        }
    }


    private fun favoriteCheck() {
        val resultById = favMovieDatabase?.favMovieDao()?.getById(tvShow.id!!)
        if (resultById?.id != null) {
            isFavorite = true
        }
    }
}
