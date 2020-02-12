package com.susiyanti.movieapp.data

import com.susiyanti.movieapp.data.mapper.MovieMapper
import com.susiyanti.movieapp.data.repository.MoviesCache
import com.susiyanti.movieapp.data.source.MoviesDataSourceFactory
import com.susiyanti.movieapp.domain.model.Movie
import com.susiyanti.movieapp.domain.repository.MoviesRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class MoviesDataRepository @Inject constructor(
        private val mapper: MovieMapper,
        private val cache: MoviesCache,
        private val factory: MoviesDataSourceFactory
) : MoviesRepository {

    override fun getMovies(): Observable<List<Movie>> {
        return Observable.zip(
            cache.areMoviesCached().toObservable(),
            cache.isMoviesCacheExpired().toObservable(),
            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { cached, expired ->
                Pair(cached, expired)
            }).flatMap {
            factory.getDataSource(it.first, it.second)
                .getMovies()
                .toObservable()
                .distinctUntilChanged()
        }.flatMap { projects ->
            factory.getCacheDataSource()
                .saveMovies(projects)
                .andThen(
                    factory.getCacheDataSource()
                        .getMovies()
                        .toObservable()
                )
        }.map { list -> list.map { mapper.mapFromEntity(it) } }
    }

    override fun favoriteMovie(movieId: String): Completable {
        return factory.getCacheDataSource().setMovieAsFavorited(movieId)
    }

    override fun unfavoriteMovie(movieId: String): Completable {
        return factory.getCacheDataSource().setMovieAsNotFavorited(movieId)
    }

    override fun getFavoritedMovies(): Observable<List<Movie>> {
        return factory.getCacheDataSource().getFavoritedMovies()
            .map { list -> list.map { mapper.mapFromEntity(it) } }
    }
}
