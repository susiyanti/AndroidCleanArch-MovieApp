package com.susiyanti.movieapp.data.repository

import com.susiyanti.movieapp.data.model.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface MoviesDataSource {

    fun getMovies(): Flowable<List<MovieEntity>>

    fun saveMovies(movies: List<MovieEntity>): Completable

    fun clearMovies(): Completable

    fun getFavoritedMovies(): Observable<List<MovieEntity>>

    fun setMovieAsFavorited(projectId: String): Completable

    fun setMovieAsNotFavorited(projectId: String): Completable
}