package com.example.movieandtv.view.view_interface

import com.example.movieandtv.model.TvShowItem

interface TvShowView {
    fun showTvShow(tvShow: List<TvShowItem>)
    fun showLoading()
    fun hideLoading()
}