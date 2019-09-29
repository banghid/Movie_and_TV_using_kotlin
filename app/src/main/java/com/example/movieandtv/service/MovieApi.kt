package com.example.movieandtv.service

import com.example.movieandtv.BuildConfig
import com.example.movieandtv.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApi {
    @GET("3/discover/movie?api_key=" + BuildConfig.API_KEY)
    fun getMovie(@Query("language") language: String): Call<MovieResponse>

    @GET("3/search/movie?api_key=" + BuildConfig.API_KEY)
    fun getSearchMovie(@Query("language") language: String, @Query("query") query: String?): Call<MovieResponse>

    @GET("3/discover/movie?api_key=" + BuildConfig.API_KEY)
    fun getNewReleaseMovie(@Query("primary_release_date.gte") dateGte: String, @Query("primary_release_date.lte") dateLte: String): Call<MovieResponse>

}