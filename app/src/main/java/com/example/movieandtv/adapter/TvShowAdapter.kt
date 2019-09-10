package com.example.movieandtv.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieandtv.R
import com.example.movieandtv.model.TvShowItem
import com.example.movieandtv.view.tvshow_detail.TvShowDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.tvshow_item_row.view.*

class TvShowAdapter(private val context: Context, private val tvShowData: ArrayList<TvShowItem>) :
    RecyclerView.Adapter<TvShowHolder>() {
    override fun onBindViewHolder(p0: TvShowHolder, p1: Int) {
        return p0.bindTvShow(context, tvShowData[p1])
    }

    override fun getItemCount(): Int = tvShowData.size

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TvShowHolder {
        return TvShowHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.tvshow_item_row,
                p0,
                false
            )
        )
    }

    fun getList(): ArrayList<TvShowItem> {
        return tvShowData
    }

}

class TvShowHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var img_tvshow_row = view.img_tvshow_row
    private var tv_title_tvshow = view.tv_title_tvshow
    private var tv_rating_tvshow = view.tv_rating_tvshow
    private var tvshow_item_row = view.tvshow_item_row
    private val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w185/"

    fun bindTvShow(context: Context, tvShow: TvShowItem) {
        tvShow.posterPath.let {
            Picasso.get().load(BASE_URL_IMAGE + tvShow.posterPath).into(img_tvshow_row)
        }

        tv_title_tvshow.text = tvShow.originalName
        tv_rating_tvshow.text = tvShow.voteAverage.toString()
//        tv_episode_tvshow.text = tvShow.
        tvshow_item_row.setOnClickListener {
            var detailTvShow = Intent(context, TvShowDetailActivity::class.java)
            detailTvShow.putExtra("TVSHOW_DATA", tvShow)
            context.startActivity(detailTvShow)
        }


    }

}