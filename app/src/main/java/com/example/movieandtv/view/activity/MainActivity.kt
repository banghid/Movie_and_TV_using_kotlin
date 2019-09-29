package com.example.movieandtv.view.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.example.movieandtv.R
import com.example.movieandtv.adapter.MovieAdapter
import com.example.movieandtv.adapter.MyPagerAdapter
import com.example.movieandtv.adapter.TvShowAdapter
import com.example.movieandtv.model.MovieItem
import com.example.movieandtv.model.TvShowItem
import com.example.movieandtv.presenter.AppSearchPresenter
import com.example.movieandtv.view.fragment.MovieFragment
import com.example.movieandtv.view.fragment.TvshowFragment
import com.example.movieandtv.view.view_interface.AppSearchView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, AppSearchView {

    private var menuItem: Menu? = null
    private var searchView: SearchView? = null
    private var movies: ArrayList<MovieItem> = arrayListOf()
    private var tvShows: ArrayList<TvShowItem> = arrayListOf()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var tvShowAdapter: TvShowAdapter
    private lateinit var appSearchPresenter: AppSearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appSearchPresenter = AppSearchPresenter(this)
        val pages = listOf(
            MovieFragment(),
            TvshowFragment()
        )
        view_pager.adapter = MyPagerAdapter(supportFragmentManager, this, pages)
        tabs.setupWithViewPager(view_pager)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        menuItem = menu
        val searchItem = menuItem?.findItem(R.id.search_icon_menu)

        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                searchMode()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                idleMode()
                return true
            }
        })
        searchView = searchItem?.getActionView() as SearchView
        searchView?.setOnQueryTextListener(this)
        searchView?.queryHint = resources.getString(R.string.input_text)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_change_settings -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            R.id.favorite_icon -> {
                val intent = Intent(applicationContext, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.search_icon_menu -> {
                if (view_pager.currentItem == 0) {
                    Toast.makeText(this, "Search Movie", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Search TV Shows", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.action_notification_setup -> {
                val intent = Intent(applicationContext, NotificationSettingActivity::class.java)
                startActivity(intent)
            }
        }
        return (super.onOptionsItemSelected(item))
    }


    private fun searchMode() {

        val favoriteMenuItem = menuItem?.findItem(R.id.favorite_icon)
        favoriteMenuItem?.isVisible = false
        tabs.visibility = View.GONE
        view_pager.visibility = View.GONE
        rv_search.visibility = View.VISIBLE
    }

    private fun idleMode() {
        val favoriteMenuItem = menuItem?.findItem(R.id.favorite_icon)
        favoriteMenuItem?.isVisible = true
        tabs.visibility = View.VISIBLE
        view_pager.visibility = View.VISIBLE
        rv_search.visibility = View.GONE
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        try {
            if (view_pager.currentItem == 0) {
                rv_search.adapter = null
                appSearchPresenter.setMovieSearch(
                    resources.getString(R.string.code_language),
                    query
                )
                movieAdapter = MovieAdapter(this, movies)

                rv_search.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    setHasFixedSize(true)
                    adapter = movieAdapter
                }
            } else {
                rv_search.adapter = null
                appSearchPresenter.setTvShowSearch(
                    resources.getString(R.string.code_language),
                    query
                )
                tvShowAdapter = TvShowAdapter(this, tvShows)

                rv_search.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    setHasFixedSize(true)
                    adapter = tvShowAdapter
                }
            }
            return true
        } catch (e: Exception) {
            Log.d("Search OnTxtSbmt", "Fail to get data " + e.message)
            return false
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        try {
            if (view_pager.currentItem == 0) {
                rv_search.adapter = null
                appSearchPresenter.setMovieSearch(
                    resources.getString(R.string.code_language),
                    newText
                )
                movieAdapter = MovieAdapter(this, movies)

                rv_search.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    setHasFixedSize(true)
                    adapter = movieAdapter
                }
            } else {
                rv_search.adapter = null
                appSearchPresenter.setTvShowSearch(
                    resources.getString(R.string.code_language),
                    newText
                )
                tvShowAdapter = TvShowAdapter(this, tvShows)

                rv_search.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    setHasFixedSize(true)
                    adapter = tvShowAdapter
                }
            }
            return true
        } catch (e: Exception) {
            Log.d("Search onTextChg", "Fail to get data " + e.message)
            return false
        }

    }

    override fun showSearchMovie(movie: List<MovieItem>) {
        this.movies.clear()
        this.movies.addAll(movie)
        movieAdapter.notifyDataSetChanged()
    }

    override fun showSearchTvShow(tvShow: List<TvShowItem>) {
        this.tvShows.clear()
        this.tvShows.addAll((tvShow))
        tvShowAdapter.notifyDataSetChanged()
    }

    override fun showSearchLoading() {
        main_search_pb.visibility = View.VISIBLE
    }

    override fun hideSearchLoading() {
        main_search_pb.visibility = View.GONE
    }

}