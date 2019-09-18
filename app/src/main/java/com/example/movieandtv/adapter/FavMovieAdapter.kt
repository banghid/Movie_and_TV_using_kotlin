package com.example.movieandtv.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieandtv.R
import com.example.movieandtv.database.MovieModelDB
import com.example.movieandtv.view.activity.MovieDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item_row.view.*

class FavMovieAdapter(
    private val context: Context,
    private val movieData: ArrayList<MovieModelDB>
) :
    RecyclerView.Adapter<FavMovieHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FavMovieHolder {
        return FavMovieHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.movie_item_row,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int = movieData.size

    override fun onBindViewHolder(p0: FavMovieHolder, p1: Int) {
        return p0.bindMovie(context, movieData[p1])
    }

    fun getList(): ArrayList<MovieModelDB> {
        return movieData
    }
}

class FavMovieHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var img_movie_row = view.img_movie_row
    private var tv_title = view.tv_title
    private var tv_rating = view.tv_rating
    private var movie_item_row = view.movie_item_row
    private val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w185/"

    fun bindMovie(context: Context, movie: MovieModelDB) {

        movie.posterPath.let {
            Picasso.get().load(BASE_URL_IMAGE + movie.posterPath).into(img_movie_row)
        }

        tv_title.text = movie.title
        tv_rating.text = movie.voteAverage.toString()

        movie_item_row.setOnClickListener {
            val detailIntent = Intent(context, MovieDetailActivity::class.java)
            detailIntent.putExtra("MOVIE_DATA", movie)
            context.startActivity(detailIntent)
        }
    }

}