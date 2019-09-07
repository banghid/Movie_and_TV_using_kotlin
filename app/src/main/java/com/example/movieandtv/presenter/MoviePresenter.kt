package com.example.movieandtv.presenter


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.example.movieandtv.R
import com.example.movieandtv.model.Movie
import com.example.movieandtv.model.MovieItem
import com.example.movieandtv.model.MovieResponse
import com.example.movieandtv.service.AppService
import com.example.movieandtv.view.movie.MovieView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MoviePresenter(private var view: MovieView) {
    private lateinit var lang: String
    private lateinit var appService: AppService
    private val listMovie = MutableLiveData<ArrayList<MovieItem>>()

    fun setMovie(lang: String) {
        this.lang = lang

        appService.getMovieApi().getMovie(lang).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponse = response.body()
                if (movieResponse?.results != null) {
                    val movieItems:ArrayList<MovieItem> = movieResponse.results
                    Log.d("MovieViewModel", "onResponse success$movieItems")
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

    fun getListMovie(context: Context){
        val moviesInEnglish = arrayListOf<Movie>(
            Movie(context.resources.getString(R.string.movie1_title),context.resources.getString(R.string.movie1_genre),context.resources.getString(R.string.movie1_poster),context.resources.getString(R.string.movie1_overview)),
            Movie(context.resources.getString(R.string.movie2_title),context.resources.getString(R.string.movie2_genre),context.resources.getString(R.string.movie2_poster),context.resources.getString(R.string.movie2_overview)),
            Movie(context.resources.getString(R.string.movie3_title),context.resources.getString(R.string.movie3_genre),context.resources.getString(R.string.movie3_poster),context.resources.getString(R.string.movie3_overview)),
            Movie(context.resources.getString(R.string.movie4_title),context.resources.getString(R.string.movie4_genre),context.resources.getString(R.string.movie4_poster),context.resources.getString(R.string.movie4_overview)),
            Movie(context.resources.getString(R.string.movie5_title),context.resources.getString(R.string.movie5_genre),context.resources.getString(R.string.movie5_poster),context.resources.getString(R.string.movie5_overview)),
            Movie(context.resources.getString(R.string.movie6_title),context.resources.getString(R.string.movie6_genre),context.resources.getString(R.string.movie6_poster),context.resources.getString(R.string.movie6_overview)),
            Movie(context.resources.getString(R.string.movie7_title),context.resources.getString(R.string.movie7_genre),context.resources.getString(R.string.movie7_poster),context.resources.getString(R.string.movie7_overview)),
            Movie(context.resources.getString(R.string.movie8_title),context.resources.getString(R.string.movie8_genre),context.resources.getString(R.string.movie8_poster),context.resources.getString(R.string.movie8_overview)),
            Movie(context.resources.getString(R.string.movie9_title),context.resources.getString(R.string.movie9_genre),context.resources.getString(R.string.movie9_poster),context.resources.getString(R.string.movie9_overview)),
            Movie(context.resources.getString(R.string.movie10_title),context.resources.getString(R.string.movie10_genre),context.resources.getString(R.string.movie10_poster),context.resources.getString(R.string.movie10_overview))
        )
        view.showMovie(moviesInEnglish)
    }
}