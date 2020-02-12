package com.susiyanti.mobileui.browse

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.susiyanti.movieapp.presentation.BrowseMoviesViewModel
import com.susiyanti.movieapp.presentation.model.MovieView
import com.susiyanti.movieapp.presentation.state.Resource
import com.susiyanti.movieapp.presentation.state.ResourceState
import com.susiyanti.mobileui.R
import com.susiyanti.mobileui.favorited.FavoritedActivity
import com.susiyanti.mobileui.ext.get
import com.susiyanti.mobileui.injection.ViewModelFactory
import com.susiyanti.mobileui.mapper.MovieViewMapper
import com.susiyanti.mobileui.model.Movie
import dagger.android.AndroidInjection
import timber.log.Timber
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : AppCompatActivity() {

    @Inject lateinit var mapper: MovieViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: BrowseMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get()

        setupBrowseRecycler()
        viewModel.getMovies().observe(this, dataStateObserver)
        viewModel.fetchMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorited -> {
                startActivity(FavoritedActivity.getStartIntent(this))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupScreenForSuccess(movies: List<Movie>?) {
        if (progress.isVisible) {
            progress.isVisible = false
        }
        movies?.let { list -> (recycler_view.adapter as? BrowseAdapter)?.submitList(list) }
    }

    private fun setupBrowseRecycler() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = BrowseAdapter().apply { movieListener = listener }
        }
    }

    private val dataStateObserver = Observer<Resource<List<MovieView>>> { resource ->
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data?.map { mapper.mapToView(it) })
            }
            ResourceState.LOADING -> {
                if (recycler_view.adapter?.itemCount ?: 0 == 0 ) {
                    progress.isVisible = true
                }
            }
            ResourceState.ERROR -> {
                resource.message?.let { Timber.e(it) }
                progress.isVisible = false
                findViewById<View?>(android.R.id.content)?.let {
                    Snackbar.make(it, resource.message ?: "", Snackbar.LENGTH_INDEFINITE).show()
                }
            }
        }
    }

    private val listener = object : MovieListener {
        override fun onFavoritedMovieClicked(projectId: String) {
            viewModel.unFavoriteProject(projectId)
        }

        override fun onMovieClicked(projectId: String) {
            viewModel.favoriteProject(projectId)
        }
    }
}
