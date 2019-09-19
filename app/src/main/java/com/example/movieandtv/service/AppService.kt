package com.example.movieandtv.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppService {
    private var retrofit: Retrofit? = null
    private val BASE_URL: String = "https://api.themoviedb.org/"

    fun getMovieApi(): MovieApi? {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit?.create<MovieApi>(MovieApi::class.java)
    }

    fun getTvshowApi(): TvShowApi? {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit?.create<TvShowApi>(TvShowApi::class.java)
    }
}