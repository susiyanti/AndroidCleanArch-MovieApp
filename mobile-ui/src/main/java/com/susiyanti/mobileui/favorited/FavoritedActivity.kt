package com.susiyanti.mobileui.favorited

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.susiyanti.movieapp.presentation.BrowseFavoritedMoviesViewModel
import com.susiyanti.movieapp.presentation.model.MovieView
import com.susiyanti.movieapp.presentation.state.Resource
import com.susiyanti.movieapp.presentation.state.ResourceState
import com.susiyanti.mobileui.R
import com.susiyanti.mobileui.ext.get
import com.susiyanti.mobileui.ext.intentOf
import com.susiyanti.mobileui.injection.ViewModelFactory
import com.susiyanti.mobileui.mapper.MovieViewMapper
import com.susiyanti.mobileui.model.Movie
import dagger.android.AndroidInjection
import javax.inject.Inject


class FavoritedActivity : AppCompatActivity() {

    @Inject lateinit var adapter: FavoritedAdapter
    @Inject lateinit var mapper: MovieViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    
    private lateinit var viewModel: BrowseFavoritedMoviesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progress: ProgressBar

    companion object {
        fun getStartIntent(context: Context): Intent {
            return intentOf<FavoritedActivity>(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorited)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get()
        recyclerView = findViewById(R.id.recycler_view)
        progress = findViewById(R.id.progress)

        setupBrowseRecycler()
        viewModel.getMovies().observe(this, dataStateObserver)
        viewModel.fetchMovies()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupBrowseRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private val dataStateObserver = Observer<Resource<List<MovieView>>> { resource ->
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data?.map { mapper.mapToView(it) })
                progress.isVisible = false
                recyclerView.isVisible = true
            }

            ResourceState.LOADING -> {
                progress.isVisible = true
                recyclerView.isVisible = false
            }

            ResourceState.ERROR -> {
            }
        }
    }

    private fun setupScreenForSuccess(movies: List<Movie>?) {
        movies?.let {
            adapter.movies = it
            adapter.notifyDataSetChanged()
        }
    }
}
