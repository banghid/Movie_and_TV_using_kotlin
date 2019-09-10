package com.example.movieandtv.view.movie

import com.example.movieandtv.model.MovieItem

interface MovieView {
    fun showMovie(movie: List<MovieItem>)
    fun showLoading()
    fun hideLoading()
}