package com.example.movieandtv.presenter


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.movieandtv.model.MovieItem
import com.example.movieandtv.model.MovieResponse
import com.example.movieandtv.service.AppService
import com.example.movieandtv.view.movie.MovieView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MoviePresenter(private var view: MovieView) {
    private lateinit var lang: String
    private var appService: AppService = AppService()
    private val listMovie = MutableLiveData<ArrayList<MovieItem>>()

    fun setMovie(lang: String) {
        this.lang = lang

        appService.getMovieApi().getMovie(lang).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponse = response.body()
                if (movieResponse?.results != null) {
                    val movieItems:ArrayList<MovieItem> = movieResponse.results
                    Log.d("MoviePresenter", "onResponse success$movieItems")
                    listMovie.postValue(movieItems)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieViewModel", "onFailure " + t.message)
            }
        })
    }

    fun getMovies(): LiveData<ArrayList<MovieItem>> {
        return listMovie
    }

}