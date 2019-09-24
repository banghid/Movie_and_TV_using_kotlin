package com.example.movieandtv.view.view_interface

import com.example.movieandtv.model.MovieItem
import com.example.movieandtv.model.TvShowItem

interface AppSearchView {
    fun showSearchMovie(movie: List<MovieItem>)
    fun showSearchTvShow(tvShow: List<TvShowItem>)
    fun showSearchLoading()
    fun hideSearchLoading()

}