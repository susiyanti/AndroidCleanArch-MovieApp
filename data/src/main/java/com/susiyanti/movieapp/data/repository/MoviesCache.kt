package com.susiyanti.movieapp.data.repository

import com.susiyanti.movieapp.data.model.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface MoviesCache {

    fun clearMovies(): Completable

    fun saveMovies(movies: List<MovieEntity>): Completable

    fun getMovies(): Flowable<List<MovieEntity>>

    fun getFavoritedMovies(): Observable<List<MovieEntity>>

    fun setMovieAsFavorited(projectId: String): Completable

    fun setMovieAsNotFavorited(projectId: String): Completable

    fun areMoviesCached(): Single<Boolean>

    fun setLastCacheTime(lastCache: Long): Completable

    fun isMoviesCacheExpired(): Single<Boolean>
}
