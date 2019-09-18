package com.example.movieandtv.view.view_interface

import com.example.movieandtv.database.MovieModelDB

interface FavMovieView {
    fun showFavMovie(movie: List<MovieModelDB>)
    fun showLoading()
    fun hideLoading()
}