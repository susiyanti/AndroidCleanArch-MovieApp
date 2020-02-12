package com.susiyanti.movieapp.cache.test.factory

import com.susiyanti.movieapp.cache.model.CachedMovie
import com.susiyanti.movieapp.data.model.MovieEntity

object MovieDataFactory {

    fun makeCachedMovie(): CachedMovie = with(DataFactory) {
        CachedMovie(randomString(), randomString(), randomString(), randomString(),false)
    }

    fun makeCachedFavoritedProject(): CachedMovie = with(DataFactory) {
        CachedMovie(randomString(), randomString(), randomString(), randomString(),true)
    }

    fun makeMovieEntity(): MovieEntity = with(DataFactory) {
        MovieEntity(randomString(), randomString(), randomString(), randomString(),false)
    }

    fun makeFavoritedMovieEntity(): MovieEntity = with(DataFactory) {
        MovieEntity(randomString(), randomString(), randomString(), randomString(),true)
    }
}
