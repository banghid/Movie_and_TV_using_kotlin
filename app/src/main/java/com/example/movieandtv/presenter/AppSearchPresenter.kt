package com.example.movieandtv.presenter

import android.util.Log
import com.example.movieandtv.model.MovieItem
import com.example.movieandtv.model.MovieResponse
import com.example.movieandtv.model.TvShowItem
import com.example.movieandtv.model.TvShowResponse
import com.example.movieandtv.service.AppService
import com.example.movieandtv.view.view_interface.AppSearchView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppSearchPresenter(private var view: AppSearchView) {

    private var appService: AppService = AppService()

    fun setMovieSearch(lang: String, queryTag: String?) {
        view.showSearchLoading()
        appService.getMovieApi()?.getSearchMovie(lang, queryTag)
            ?.enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    val movieResponse = response.body()
                    if (movieResponse?.results != null) {
                        val movieItems: ArrayList<MovieItem> = movieResponse.results
                        Log.d("AppSearch", "onResponse success$movieItems")
                        view.showSearchMovie(movieItems)
                        view.hideSearchLoading()
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    view.hideSearchLoading()
                    Log.d("AppSearch get data", "Fail to get Data movie " + t.message)
                }
            })
    }

    fun setTvShowSearch(lang: String, queryTag: String?) {
        view.showSearchLoading()
        appService.getTvshowApi()?.getSearchTvShow(lang, queryTag)
            ?.enqueue(object : Callback<TvShowResponse> {
                override fun onResponse(
                    call: Call<TvShowResponse>,
                    response: Response<TvShowResponse>
                ) {
                    val tvShowResponse = response.body()
                    if (tvShowResponse?.results != null) {
                        val tvShowItems: ArrayList<TvShowItem> = tvShowResponse.results
                        view.showSearchTvShow(tvShowItems)
                        view.hideSearchLoading()

                    }
                }

                override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                    view.hideSearchLoading()
                    Log.d("AppSearch get data", "Fail to get data TV show " + t.message)
                }
            })
    }
}