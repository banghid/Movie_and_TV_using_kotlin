package com.example.movieandtv.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieandtv.R
import com.example.movieandtv.adapter.FavMovieAdapter
import com.example.movieandtv.database.FavMovieDatabase
import com.example.movieandtv.database.MovieModelDB
import com.example.movieandtv.presenter.FavMoviePresenter
import com.example.movieandtv.view.view_interface.FavMovieView
import kotlinx.android.synthetic.main.fragment_favorite_movie.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment : Fragment(), FavMovieView {


    private var movies: ArrayList<MovieModelDB> = ArrayList()
    private lateinit var favMoviePresenter: FavMoviePresenter
    private lateinit var favMovieAdapter: FavMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favMovieDatabase: FavMovieDatabase = FavMovieDatabase.getInstance(this.context!!)!!
        favMoviePresenter = FavMoviePresenter(this, favMovieDatabase)

        if (savedInstanceState?.getParcelableArrayList<MovieModelDB>("data") != null) {
            val movieData: ArrayList<MovieModelDB> =
                ArrayList(savedInstanceState.getParcelableArrayList("data")!!)
            favMovieAdapter = FavMovieAdapter(
                view.context,
                movieData
            )
            favMovieAdapter.notifyDataSetChanged()
        } else {
            favMovieAdapter = FavMovieAdapter(view.context, movies)
            favMoviePresenter.getAllData("movie")
            Log.d("MoviePresenter", "not null")
        }

        rv_fav_movie.apply {
            layoutManager = LinearLayoutManager(view.context)
            setHasFixedSize(true)
            adapter = favMovieAdapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("data", favMovieAdapter.getList())
        super.onSaveInstanceState(outState)
    }

    override fun showFavMovie(movie: List<MovieModelDB>) {
        this.movies.addAll(movie)
        favMovieAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        fragment_fav_movies_pb.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        fragment_fav_movies_pb.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        FavMovieDatabase.destroyInstance()
    }

}
