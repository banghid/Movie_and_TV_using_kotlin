package com.example.movieandtv.presenter

import android.util.Log
import com.example.movieandtv.database.FavMovieDatabase
import com.example.movieandtv.database.MovieModelDB
import com.example.movieandtv.view.view_interface.FavMovieView

class FavMoviePresenter(
    private var view: FavMovieView,
    private val favMovieDatabase: FavMovieDatabase
) {

    fun getAllData(category: String) {

        try {
            view.showLoading()
            val movieItems: ArrayList<MovieModelDB> = ArrayList()
            movieItems.addAll(favMovieDatabase.favMovieDao().getAllMovies(category))
            view.showFavMovie(movieItems)
            view.hideLoading()
            Log.d("FavMovPresenter GetAll", "Success to getAll")
        } catch (e: Exception) {
            Log.d("FavMovPresenter GetAll", "Fail to getAll" + e.message)
        }
    }

}