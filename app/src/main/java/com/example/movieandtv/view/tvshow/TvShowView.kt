package com.example.movieandtv.view.tvshow

import com.example.movieandtv.model.TvShowItem

interface TvShowView {
    fun showTvShow(tvShow: List<TvShowItem>)
    fun showLoading()
    fun hideLoading()
}