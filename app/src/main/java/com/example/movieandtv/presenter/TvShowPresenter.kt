package com.example.movieandtv.presenter

import android.util.Log
import com.example.movieandtv.model.TvShowItem
import com.example.movieandtv.model.TvShowResponse
import com.example.movieandtv.service.AppService
import com.example.movieandtv.view.view_interface.TvShowView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TvShowPresenter(private var view: TvShowView) {
    private var appService: AppService = AppService()

    fun setTvShow(lang: String) {
        view.showLoading()
        appService.getTvshowApi().getTvShow(lang).enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(
                call: Call<TvShowResponse>,
                response: Response<TvShowResponse>
            ) {
                val tvShowResponse = response.body()
                if (tvShowResponse?.results != null) {
                    val tvShowItems: ArrayList<TvShowItem> = tvShowResponse.results
                    Log.d("TvShowPresenter", "onResponse success$tvShowItems")
                    view.showTvShow(tvShowItems)
                    view.hideLoading()
//                    listTvShow.postValue(tvShowItems)
                }
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                view.hideLoading()
                Log.d("TvShowPresenter", "onFailure " + t.message + " " + lang)
            }
        })
    }

}