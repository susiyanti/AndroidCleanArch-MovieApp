package com.susiyanti.movieapp.data.source

import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.data.repository.MoviesDataSource
import com.susiyanti.movieapp.data.repository.MoviesRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
        private val moviesRemote: MoviesRemote
) : MoviesDataSource {

    override fun getMovies(): Flowable<List<MovieEntity>> {
        return moviesRemote.getMovies()
    }

    override fun saveMovies(movies: List<MovieEntity>): Completable {
        throw UnsupportedOperationException("Saving projects isn't supported here...")
    }

    override fun clearMovies(): Completable {
        throw UnsupportedOperationException("Clearing projects isn't supported here...")
    }

    override fun getFavoritedMovies(): Observable<List<MovieEntity>> {
        throw UnsupportedOperationException("Getting bookmarked projects isn't supported here...")
    }

    override fun setMovieAsFavorited(projectId: String): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }

    override fun setMovieAsNotFavorited(projectId: String): Completable {
        throw UnsupportedOperationException("Setting bookmarks isn't supported here...")
    }
}