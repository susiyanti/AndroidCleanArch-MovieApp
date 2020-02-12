package com.susiyanti.movieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.susiyanti.movieapp.domain.interactor.favorite.GetFavoritedMovies
import com.susiyanti.movieapp.domain.model.Movie
import com.susiyanti.movieapp.presentation.mapper.MovieViewMapper
import com.susiyanti.movieapp.presentation.model.MovieView
import com.susiyanti.movieapp.presentation.state.Resource
import com.susiyanti.movieapp.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseFavoritedMoviesViewModel @Inject constructor(
        private val getFavoritedMovies: GetFavoritedMovies,
        private val mapper: MovieViewMapper) : ViewModel() {


    private val liveData: MutableLiveData<Resource<List<MovieView>>> = MutableLiveData()

    override fun onCleared() {
        getFavoritedMovies.dispose()
        super.onCleared()
    }

    fun getMovies(): LiveData<Resource<List<MovieView>>> {
        return liveData
    }

    fun fetchMovies() {
        liveData.postValue(Resource(ResourceState.LOADING))
        getFavoritedMovies.execute(ProjectsSubscriber())
    }

    inner class ProjectsSubscriber : DisposableObserver<List<Movie>>() {
        override fun onComplete() {}

        override fun onNext(t: List<Movie>) {
            liveData.postValue(Resource(
                    ResourceState.SUCCESS,
                    data = t.map { mapper.mapToView(it) }))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(
                    ResourceState.ERROR,
                    message = e.localizedMessage))
        }
    }
}
