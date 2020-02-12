package com.susiyanti.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.susiyanti.movieapp.domain.interactor.favorite.FavoriteMovie
import com.susiyanti.movieapp.domain.interactor.favorite.UnFavoriteMovie
import com.susiyanti.movieapp.domain.interactor.browse.GetMovies
import com.susiyanti.movieapp.domain.model.Movie
import com.susiyanti.movieapp.presentation.mapper.MovieViewMapper
import com.susiyanti.movieapp.presentation.model.MovieView
import com.susiyanti.movieapp.presentation.state.Resource
import com.susiyanti.movieapp.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseMoviesViewModel @Inject constructor(
        private val getMovies: GetMovies,
        private val favoriteMovie: FavoriteMovie,
        private val unFavoriteMovie: UnFavoriteMovie,
        private val mapper: MovieViewMapper): ViewModel() {

    private val liveData: MutableLiveData<Resource<List<MovieView>>> = MutableLiveData()

    override fun onCleared() {
        getMovies.dispose()
        super.onCleared()
    }

    fun getMovies(): LiveData<Resource<List<MovieView>>> {
        return liveData
    }

    fun fetchMovies() {
        liveData.postValue(Resource(ResourceState.LOADING))
        getMovies.execute(ProjectsSubscriber())
    }

    fun favoriteProject(projectId: String) {
        liveData.postValue(Resource(ResourceState.LOADING))
        favoriteMovie.execute(
                FavoriteProjectsSubscriber(),
                FavoriteMovie.Params.forProject(projectId))
    }

    fun unFavoriteProject(projectId: String) {
        liveData.postValue(Resource(ResourceState.LOADING))
        unFavoriteMovie.execute(
                FavoriteProjectsSubscriber(),
                UnFavoriteMovie.Params.forProject(projectId))
    }

    inner class ProjectsSubscriber : DisposableObserver<List<Movie>>() {
        override fun onComplete() {}

        override fun onNext(t: List<Movie>) {
            liveData.postValue(Resource(
                    ResourceState.SUCCESS,
                    t.map { mapper.mapToView(it) }))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(
                    ResourceState.ERROR,
                    message = e.localizedMessage))
        }
    }

    inner class FavoriteProjectsSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {
            liveData.postValue(Resource(
                    ResourceState.SUCCESS))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(
                    ResourceState.ERROR,
                    message = e.localizedMessage))
        }
    }
}