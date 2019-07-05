package com.example.movieandtv.presenter

import android.content.Context
import com.example.movieandtv.R
import com.example.movieandtv.model.TvShow
import com.example.movieandtv.view.tvshow.TvShowView

class TvShowPresenter {

    private var view: TvShowView

    constructor(view: TvShowView){
        this.view = view
    }

    fun getListTvShow(context: Context){
        val tvShows = listOf<TvShow>(
            TvShow(context.resources.getString(R.string.tvshow1_title),context.resources.getString(R.string.tvshow1_genre),context.resources.getString(R.string.tvshow1_poster),context.resources.getString(R.string.tvshow1_episode),context.resources.getString(R.string.tvshow1_overview)),
            TvShow(context.resources.getString(R.string.tvshow2_title),context.resources.getString(R.string.tvshow2_genre),context.resources.getString(R.string.tvshow2_poster),context.resources.getString(R.string.tvshow2_episode),context.resources.getString(R.string.tvshow2_overview)),
            TvShow(context.resources.getString(R.string.tvshow3_title),context.resources.getString(R.string.tvshow3_genre),context.resources.getString(R.string.tvshow3_poster),context.resources.getString(R.string.tvshow3_episode),context.resources.getString(R.string.tvshow3_overview)),
            TvShow(context.resources.getString(R.string.tvshow4_title),context.resources.getString(R.string.tvshow4_genre),context.resources.getString(R.string.tvshow4_poster),context.resources.getString(R.string.tvshow4_episode),context.resources.getString(R.string.tvshow4_overview)),
            TvShow(context.resources.getString(R.string.tvshow5_title),context.resources.getString(R.string.tvshow5_genre),context.resources.getString(R.string.tvshow5_poster),context.resources.getString(R.string.tvshow5_episode),context.resources.getString(R.string.tvshow5_overview)),
            TvShow(context.resources.getString(R.string.tvshow1_title),context.resources.getString(R.string.tvshow1_genre),context.resources.getString(R.string.tvshow1_poster),context.resources.getString(R.string.tvshow1_episode),context.resources.getString(R.string.tvshow1_overview)),
            TvShow(context.resources.getString(R.string.tvshow2_title),context.resources.getString(R.string.tvshow2_genre),context.resources.getString(R.string.tvshow2_poster),context.resources.getString(R.string.tvshow2_episode),context.resources.getString(R.string.tvshow2_overview)),
            TvShow(context.resources.getString(R.string.tvshow3_title),context.resources.getString(R.string.tvshow3_genre),context.resources.getString(R.string.tvshow3_poster),context.resources.getString(R.string.tvshow3_episode),context.resources.getString(R.string.tvshow3_overview)),
            TvShow(context.resources.getString(R.string.tvshow4_title),context.resources.getString(R.string.tvshow4_genre),context.resources.getString(R.string.tvshow4_poster),context.resources.getString(R.string.tvshow4_episode),context.resources.getString(R.string.tvshow4_overview)),
            TvShow(context.resources.getString(R.string.tvshow5_title),context.resources.getString(R.string.tvshow5_genre),context.resources.getString(R.string.tvshow5_poster),context.resources.getString(R.string.tvshow5_episode),context.resources.getString(R.string.tvshow5_overview))
        )

        view.showTvShows(tvShows)
    }
}