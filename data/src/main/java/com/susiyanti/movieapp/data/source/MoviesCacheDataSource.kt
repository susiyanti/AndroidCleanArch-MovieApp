package com.susiyanti.movieapp.data.source

import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.data.repository.MoviesCache
import com.susiyanti.movieapp.data.repository.MoviesDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class MoviesCacheDataSource @Inject constructor(
        private val moviesCache: MoviesCache
) : MoviesDataSource {

    override fun getMovies(): Flowable<List<MovieEntity>> {
        return moviesCache.getMovies()
    }

    override fun saveMovies(movies: List<MovieEntity>): Completable {
        return moviesCache.saveMovies(movies)
                .andThen(moviesCache.setLastCacheTime(System.currentTimeMillis()))
    }

    override fun clearMovies(): Completable {
        return moviesCache.clearMovies()
    }

    override fun getFavoritedMovies(): Observable<List<MovieEntity>> {
        return moviesCache.getFavoritedMovies()
    }

    override fun setMovieAsFavorited(projectId: String): Completable {
        return moviesCache.setMovieAsFavorited(projectId)
    }

    override fun setMovieAsNotFavorited(projectId: String): Completable {
        return moviesCache.setMovieAsNotFavorited(projectId)
    }
}