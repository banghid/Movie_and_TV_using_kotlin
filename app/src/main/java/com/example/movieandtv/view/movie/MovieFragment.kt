package com.example.movieandtv.view.movie


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieandtv.R
import com.example.movieandtv.adapter.MovieAdapter
import com.example.movieandtv.model.MovieItem
import com.example.movieandtv.presenter.MoviePresenter
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
    private lateinit var moviePresenter:MoviePresenter
    private lateinit var movieAdapter:MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviePresenter = MoviePresenter(this)
        movieAdapter = MovieAdapter(view.context,movies)
        rv_movie.apply {
            layoutManager = LinearLayoutManager(view.context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
        moviePresenter.setMovie(resources.getString(R.string.code_language))
        moviePresenter.getMovies().observe(this, getMovie)

    }

    override fun showMovie(movie: List<MovieItem>) {
        this.movies.addAll(movie)
        movieAdapter.notifyDataSetChanged()
    }

    private val getMovie =
        Observer<java.util.ArrayList<MovieItem>> { movieItems ->
            if (movieItems != null) {
                showMovie(movieItems as List<MovieItem>)
                showLoading(false)
            } else {
                showLoading(true)
            }
        }

    private fun showLoading(state: Boolean) {
        if (state) {
            fragment_movies_pb.visibility = View.VISIBLE
        } else {
            fragment_movies_pb.visibility = View.GONE
        }
    }


}
