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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_fav_detail.*


class FavDetailActivity : AppCompatActivity() {

    private lateinit var favData: MovieModelDB
    private val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/"
    private var favMovieDatabase: FavMovieDatabase? = null
    private var isFavorite: Boolean = false
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_detail)

        val intent = intent
        favData = intent.getParcelableExtra("FAVORITE_DATA")
        favData.posterPath.let {
            Picasso.get().load(BASE_URL_IMAGE + favData.posterPath).into(img_detail_fav)
        }

        tv_title_detail_fav.text = favData.title
        tv_rating_detail_fav.text = favData.voteAverage.toString()
        tv_overview_detail_fav.text = favData.overview
        favMovieDatabase = FavMovieDatabase.getInstance(this)
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
                deleteFavorite(favData)
                isFavorite = false
                setFavorite()
                Toast.makeText(this, "Success to Unfavorite", Toast.LENGTH_SHORT).show()
            } else {
                insertToDB(favData)
                isFavorite = true
                setFavorite()
                Toast.makeText(this, "Berhasil Favorite", Toast.LENGTH_SHORT).show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun insertToDB(favoriteData: MovieModelDB) {
        try {
            favMovieDatabase?.favMovieDao()?.insert(favoriteData)
            Log.d("MovieDetail Insert", "Success to save")
        } catch (e: Exception) {
            Log.d("MovieDetail Insert", "Fail to save" + e.message)
        }

    }

    fun deleteFavorite(favoriteData: MovieModelDB) {

        try {
            favMovieDatabase!!.favMovieDao().delete(favoriteData)
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
        val resultById = favMovieDatabase?.favMovieDao()?.getById(favData.id!!)
        if (resultById?.id != null) {
            isFavorite = true
        }
    }
}
