package com.example.movieandtv.service

import com.example.movieandtv.BuildConfig
import com.example.movieandtv.model.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowApi {
    @GET("3/discover/tv?api_key=" + BuildConfig.API_KEY)
    fun getTvShow(@Query("language") language: String): Call<TvShowResponse>

    @GET("3/search/tv?api_key=" + BuildConfig.API_KEY)
    fun getSearchTvShow(@Query("language") language: String, @Query("query") query: String?): Call<TvShowResponse>
}