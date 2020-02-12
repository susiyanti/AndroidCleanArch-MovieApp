package com.susiyanti.movieapp.cache

import com.susiyanti.movieapp.cache.db.MoviesDatabase
import com.susiyanti.movieapp.cache.mapper.CachedMovieMapper
import com.susiyanti.movieapp.cache.model.Config
import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.data.repository.MoviesCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject


class MoviesCacheImpl @Inject constructor(
        private val moviesDatabase: MoviesDatabase,
        private val mapper: CachedMovieMapper) : MoviesCache {

    override fun clearMovies(): Completable {
        return Completable.defer {
            moviesDatabase.cachedMoviesDao().deleteMovies()
            Completable.complete()
        }
    }

    override fun saveMovies(movies: List<MovieEntity>): Completable {
        return Completable.defer {
            moviesDatabase.cachedMoviesDao().insertMovies(
                    movies.map { mapper.mapToCached(it) })
            Completable.complete()
        }
    }

    override fun getMovies(): Flowable<List<MovieEntity>> {
        return moviesDatabase.cachedMoviesDao().getMovies()
                .map { list -> list.map { mapper.mapFromCached(it) } }
    }

    override fun getFavoritedMovies(): Observable<List<MovieEntity>> {
        return moviesDatabase.cachedMoviesDao()
                .getFavoritedMovies()
                .toObservable()
                .map { list -> list.map { mapper.mapFromCached(it) } }
    }

    override fun setMovieAsFavorited(projectId: String): Completable {
        return Completable.defer {
            moviesDatabase.cachedMoviesDao().setFavoriteStatus(true, projectId)
            Completable.complete()
        }
    }

    override fun setMovieAsNotFavorited(projectId: String): Completable {
        return Completable.defer {
            moviesDatabase.cachedMoviesDao().setFavoriteStatus(false, projectId)
            Completable.complete()
        }
    }

    override fun areMoviesCached(): Single<Boolean> {
        return moviesDatabase.cachedMoviesDao()
                .cachedMoviesExist()
                .map { it > 0 }
    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        return Completable.defer {
            moviesDatabase.configDao().insertConfig(Config(lastCacheTime = lastCache))
            Completable.complete()
        }
    }

    override fun isMoviesCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        val expirationTime = (60 * 10 * 1000).toLong()
        return moviesDatabase.configDao().getConfig()
                .onErrorReturn { Config(lastCacheTime = 0) }
                .map { currentTime - it.lastCacheTime > expirationTime }
    }
}
