package com.example.movieandtv.view.tvshow


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieandtv.R
import com.example.movieandtv.adapter.TvShowAdapter
import com.example.movieandtv.model.TvShow
import com.example.movieandtv.presenter.TvShowPresenter
import kotlinx.android.synthetic.main.fragment_tvshow.*



//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// *
// */
class TvshowFragment : Fragment(), TvShowView {

    private var tvShows:ArrayList<TvShow> = arrayListOf()
    private lateinit var tvShowPresenter: TvShowPresenter
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvShowPresenter = TvShowPresenter(this)
        tvShowAdapter = TvShowAdapter(view.context,tvShows)
        rv_tvshow.apply {
            layoutManager = LinearLayoutManager(view.context)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
        tvShowPresenter.getListTvShow(this.context!!)


    }

    override fun showTvShows(tvShows: List<TvShow>) {
        this.tvShows.addAll(tvShows)
        tvShowAdapter.notifyDataSetChanged()
    }


}
