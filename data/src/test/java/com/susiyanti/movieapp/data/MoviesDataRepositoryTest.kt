package com.susiyanti.movieapp.data

import com.susiyanti.movieapp.data.mapper.MovieMapper
import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.data.repository.MoviesCache
import com.susiyanti.movieapp.data.repository.MoviesDataSource
import com.susiyanti.movieapp.data.source.MoviesDataSourceFactory
import com.susiyanti.movieapp.data.test.factory.DataFactory
import com.susiyanti.movieapp.data.test.factory.MovieFactory
import com.susiyanti.movieapp.domain.model.Movie
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MoviesDataRepositoryTest {

    private val mapper = mock<MovieMapper>()
    private val factory = mock<MoviesDataSourceFactory>()
    private val store = mock<MoviesDataSource>()
    private val cache = mock<MoviesCache>()
    private val repository = MoviesDataRepository(mapper, cache, factory)

    @Before fun setup() {
        stubFactoryGetDataSource()
        stubFactoryGetCacheDataSource()
        stubIsCacheExpired(Single.just(false))
        stubAreMoviesCached(Single.just(false))
        stubSaveMovies(Completable.complete())
    }

    @Test fun getMoviesCompletes() {
        stubGetMovies(Flowable.just(listOf(MovieFactory.makeMovieEntity())))
        stubMapper(MovieFactory.makeMovie(), any())

        val testObserver = repository.getMovies().test()
        testObserver.assertComplete()
    }

    @Test fun getMoviesReturnsData() {
        val movieEntity = MovieFactory.makeMovieEntity()
        val movie = MovieFactory.makeMovie()
        stubGetMovies(Flowable.just(listOf(movieEntity)))
        stubMapper(movie, movieEntity)

        val testObserver = repository.getMovies().test()
        testObserver.assertValue(listOf(movie))
    }

    @Test fun getFavoritedMoviesCompletes() {
        stubGetFavoritedMovies(Observable.just(listOf(MovieFactory.makeMovieEntity())))
        stubMapper(MovieFactory.makeMovie(), any())

        val testObserver = repository.getFavoritedMovies().test()
        testObserver.assertComplete()
    }

    @Test fun getFavoritedMoviesReturnsData() {
        val movieEntity = MovieFactory.makeMovieEntity()
        val movie = MovieFactory.makeMovie()
        stubGetFavoritedMovies(Observable.just(listOf(movieEntity)))
        stubMapper(movie, movieEntity)

        val testObserver = repository.getFavoritedMovies().test()
        testObserver.assertValue(listOf(movie))
    }

    @Test fun favoriteMovieCompletes() {
        stubFavoritedMovie(Completable.complete())

        val testObserver = repository.favoriteMovie(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test fun unFavoriteMovieCompletes() {
        stubUnbookmarkProject(Completable.complete())

        val testObserver = repository.unfavoriteMovie(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    private fun stubFavoritedMovie(completable: Completable) {
        whenever(store.setMovieAsFavorited(any())) doReturn completable
    }

    private fun stubUnbookmarkProject(completable: Completable) {
        whenever(store.setMovieAsNotFavorited(any())) doReturn completable
    }

    private fun stubIsCacheExpired(single: Single<Boolean>) {
        whenever(cache.isMoviesCacheExpired()) doReturn single
    }

    private fun stubAreMoviesCached(single: Single<Boolean>) {
        whenever(cache.areMoviesCached()) doReturn single
    }

    private fun stubMapper(model: Movie, entity: MovieEntity) {
        whenever(mapper.mapFromEntity(entity)) doReturn model
    }

    private fun stubGetMovies(stub: Flowable<List<MovieEntity>>) {
        whenever(store.getMovies()) doReturn stub
    }

    private fun stubGetFavoritedMovies(observable: Observable<List<MovieEntity>>) {
        whenever(store.getFavoritedMovies()) doReturn observable
    }

    private fun stubFactoryGetDataSource() {
        whenever(factory.getDataSource(any(), any())) doReturn store
    }

    private fun stubFactoryGetCacheDataSource() {
        whenever(factory.getCacheDataSource()) doReturn store
    }

    private fun stubSaveMovies(completable: Completable) {
        whenever(store.saveMovies(any())) doReturn completable
    }
}
