package com.example.movieandtv.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieandtv.R
import com.example.movieandtv.adapter.MovieAdapter
import com.example.movieandtv.model.MovieItem
import com.example.movieandtv.presenter.MoviePresenter
import com.example.movieandtv.view.view_interface.MovieView
import kotlinx.android.synthetic.main.fragment_movie.*


//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// *
// */

class MovieFragment : Fragment(), MovieView {

    private var movies: ArrayList<MovieItem> = arrayListOf()
    private lateinit var moviePresenter: MoviePresenter
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviePresenter = MoviePresenter(this)

        if (savedInstanceState?.getParcelableArrayList<MovieItem>("data") != null) {
            movieAdapter = MovieAdapter(
                view.context,
                savedInstanceState?.getParcelableArrayList<MovieItem>("data")
            )
            movieAdapter.notifyDataSetChanged()
        } else {
            movieAdapter = MovieAdapter(view.context, movies)
            moviePresenter.setMovie(resources.getString(R.string.code_language))
            Log.d("MoviePresenter", "not null")
        }

        rv_movie.apply {
            layoutManager = LinearLayoutManager(view.context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("data", movieAdapter.getList())
        super.onSaveInstanceState(outState)
    }


    override fun showMovie(movie: List<MovieItem>) {
        this.movies.addAll(movie)
        movieAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        fragment_movies_pb.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        fragment_movies_pb.visibility = View.GONE
    }


}
