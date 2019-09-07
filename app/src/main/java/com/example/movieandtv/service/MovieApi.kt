package com.example.movieandtv.service

import com.example.movieandtv.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("3/discover/movie?api_key=43ecc6a817abcc1234a702462df4db81")
    fun getMovie(@Query("language") language: String): Call<MovieResponse>
}