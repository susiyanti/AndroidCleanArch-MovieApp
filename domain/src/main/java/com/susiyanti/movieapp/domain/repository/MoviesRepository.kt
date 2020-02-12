package com.susiyanti.movieapp.domain.repository

import com.susiyanti.movieapp.domain.model.Movie
import io.reactivex.Completable
import io.reactivex.Observable

interface MoviesRepository {
    fun getMovies(): Observable<List<Movie>>
    fun favoriteMovie(movieId: String): Completable
    fun unfavoriteMovie(movieId: String): Completable
    fun getFavoritedMovies(): Observable<List<Movie>>
}