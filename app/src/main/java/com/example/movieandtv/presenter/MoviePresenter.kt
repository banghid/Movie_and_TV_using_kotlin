package com.example.movieandtv.presenter


import android.util.Log
import com.example.movieandtv.model.MovieItem
import com.example.movieandtv.model.MovieResponse
import com.example.movieandtv.service.AppService
import com.example.movieandtv.view.view_interface.MovieView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviePresenter(private var view: MovieView) {

    private var appService: AppService = AppService()

    fun setMovie(lang: String) {
        view.showLoading()
        appService.getMovieApi()?.getMovie(lang)?.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponse = response.body()
                if (movieResponse?.results != null) {
                    val movieItems: ArrayList<MovieItem> = movieResponse.results
                    Log.d("MoviePresenter", "onResponse success$movieItems")
                    view.showMovie(movieItems)
                    view.hideLoading()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                view.hideLoading()
                Log.d("MovieViewModel", "onFailure " + t.message)
            }
        })
    }


}