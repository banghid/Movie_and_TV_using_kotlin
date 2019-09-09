package com.example.movieandtv.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieandtv.R
import com.example.movieandtv.model.MovieItem
import com.example.movieandtv.view.movie_detail.MovieDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item_row.view.*

class MovieAdapter(private val context: Context, private val movieData: List<MovieItem>) :
    RecyclerView.Adapter<MovieHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieHolder {
        return MovieHolder(LayoutInflater.from(p0.context).inflate(R.layout.movie_item_row,p0,false))
    }

    override fun getItemCount(): Int = movieData.size

    override fun onBindViewHolder(p0: MovieHolder, p1: Int) {
        return p0.bindMovie(context,movieData[p1])
    }

}

class MovieHolder(view: View): RecyclerView.ViewHolder(view){
    private var img_movie_row = view.img_movie_row
    private var tv_title = view.tv_title
    private var tv_rating = view.tv_rating
    private var movie_item_row = view.movie_item_row
    private val BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w185/"

    fun bindMovie(context: Context, movie: MovieItem) {

        movie.posterPath.let {
            Picasso.get().load(BASE_URL_IMAGE + movie.posterPath).into(img_movie_row)
        }

        tv_title.text = movie.title
        tv_rating.text = movie.voteAverage.toString()

        movie_item_row.setOnClickListener {
            var detailIntent = Intent(context, MovieDetailActivity::class.java)
            detailIntent.putExtra("MOVIE_DATA", movie)
            context.startActivity(detailIntent)
        }
    }
}