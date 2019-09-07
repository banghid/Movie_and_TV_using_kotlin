package com.example.movieandtv.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppService {
    private var retrofit: Retrofit? = null

    fun getMovieApi(): MovieApi {
        val BASE_URL = "https://api.themoviedb.org/"

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!.create<MovieApi>(MovieApi::class.java)
    }
}