package com.example.movieandtv.presenter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.movieandtv.model.TvShowItem
import com.example.movieandtv.model.TvShowResponse
import com.example.movieandtv.service.AppService
import com.example.movieandtv.view.tvshow.TvShowView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TvShowPresenter(private var view: TvShowView) {
    private lateinit var lang: String
    private var appService: AppService = AppService()
    private val listTvShow = MutableLiveData<ArrayList<TvShowItem>>()

    fun setTvShow(lang: String) {
        this.lang = lang

        appService.getTvshowApi().getTvShow(lang).enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(
                call: Call<TvShowResponse>,
                response: Response<TvShowResponse>
            ) {
                val tvShowResponse = response.body()
                if (tvShowResponse?.results != null) {
                    val tvShowItems: ArrayList<TvShowItem> = tvShowResponse.results
                    Log.d("TvShowPresenter", "onResponse success$tvShowItems")
                    listTvShow.postValue(tvShowItems)
                }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.d("TvShowPresenter", "onFailure " + t.message + " " + lang)
            }
        })
    }

    fun getTvShows(): LiveData<ArrayList<TvShowItem>> {
        return listTvShow
    }

}