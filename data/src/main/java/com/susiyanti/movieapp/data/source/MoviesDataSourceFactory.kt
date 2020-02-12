package com.susiyanti.movieapp.data.source

import com.susiyanti.movieapp.data.repository.MoviesDataSource
import javax.inject.Inject

open class MoviesDataSourceFactory @Inject constructor(
        private val moviesCacheDataSource: MoviesCacheDataSource,
        private val moviesRemoteDataSource: MoviesRemoteDataSource
) {
    open fun getDataSource(projectsCached: Boolean,
                          cacheExpired: Boolean): MoviesDataSource {
        return if (projectsCached && !cacheExpired) {
            moviesCacheDataSource
        } else {
            moviesRemoteDataSource
        }
    }

    open fun getCacheDataSource(): MoviesDataSource {
        return moviesCacheDataSource
    }
}
