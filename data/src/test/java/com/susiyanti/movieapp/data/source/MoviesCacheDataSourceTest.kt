package com.susiyanti.movieapp.data.source

import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.data.repository.MoviesCache
import com.susiyanti.movieapp.data.test.factory.DataFactory
import com.susiyanti.movieapp.data.test.factory.MovieFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class MoviesCacheDataSourceTest {

    private val cache = mock<MoviesCache>()
    private val store = MoviesCacheDataSource(cache)

    @Test fun getMoviesCompletes() {
        stubMoviesCacheGetMovies(Flowable.just(listOf(MovieFactory.makeMovieEntity())))
        val testObserver = store.getMovies().test()
        testObserver.assertComplete()
    }

    @Test fun getMoviesReturnsData() {
        val data = listOf(MovieFactory.makeMovieEntity())
        stubMoviesCacheGetMovies(Flowable.just(data))
        val testObserver = store.getMovies().test()
        testObserver.assertValue(data)
    }

    @Test fun getMoviesCallCacheSource() {
        stubMoviesCacheGetMovies(Flowable.just(listOf(MovieFactory.makeMovieEntity())))
        store.getMovies().test()
        verify(cache).getMovies()
    }

    @Test fun saveMoviesCompletes() {
        stubMoviesCacheSaveMovies(Completable.complete())
        stubMoviesCacheSetLastCacheTime(Completable.complete())
        val testObserver = store.saveMovies(listOf(MovieFactory.makeMovieEntity())).test()
        testObserver.assertComplete()
    }

    @Test fun saveMoviesCallCache() {
        val data = listOf(MovieFactory.makeMovieEntity())
        stubMoviesCacheSaveMovies(Completable.complete())
        stubMoviesCacheSetLastCacheTime(Completable.complete())
        store.saveMovies(data).test()
        verify(cache).saveMovies(data)
    }

    @Test fun saveMoviesCallCacheStore() {
        val data = listOf(MovieFactory.makeMovieEntity())
        stubMoviesCacheSaveMovies(Completable.complete())
        stubMoviesCacheSetLastCacheTime(Completable.complete())
        store.saveMovies(data).test()
        verify(cache).saveMovies(data)
    }

    @Test fun clearMoviesCompletes() {
        stubMoviesClearMovies(Completable.complete())
        val testObserver = store.clearMovies().test()
        testObserver.assertComplete()
    }

    @Test fun clearMoviesCallsCacheStore() {
        stubMoviesClearMovies(Completable.complete())
        store.clearMovies().test()
        verify(cache).clearMovies()
    }

    @Test fun getFavoritedMoviesCompletes() {
        stubMoviesCacheGetFavoritedMovies(Observable.just(listOf(
                MovieFactory.makeMovieEntity())))
        val testObserver = store.getFavoritedMovies().test()
        testObserver.assertComplete()
    }

    @Test fun getFavoritedMoviesCallsCacheStore() {
        stubMoviesCacheGetFavoritedMovies(Observable.just(listOf(
                MovieFactory.makeMovieEntity())))
        store.getFavoritedMovies().test()
        verify(cache).getFavoritedMovies()
    }

    @Test fun getFavoritedMoviesReturnsData() {
        val data = listOf(MovieFactory.makeMovieEntity())
        stubMoviesCacheGetFavoritedMovies(Observable.just(data))
        val testObserver = store.getFavoritedMovies().test()
        testObserver.assertValue(data)
    }

    @Test fun setMovieAsFavoritedCompletes() {
        stubMoviesCacheSetMoviesAsFavorited(Completable.complete())
        val testObserver = store.setMovieAsFavorited(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test fun setMovieAsFavoritedCallsCacheStore() {
        val MovieId = DataFactory.randomString()
        stubMoviesCacheSetMoviesAsFavorited(Completable.complete())
        store.setMovieAsFavorited(MovieId).test()
        verify(cache).setMovieAsFavorited(MovieId)
    }

    @Test fun setMovieAsNotFavoritedCompletes() {
        stubMoviesCacheSetMoviesAsNotFavorited(Completable.complete())
        val testObserver = store.setMovieAsNotFavorited(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test fun setMovieAsNotFavoritedCallsCacheStore() {
        val MovieId = DataFactory.randomString()
        stubMoviesCacheSetMoviesAsNotFavorited(Completable.complete())
        store.setMovieAsNotFavorited(MovieId).test()
        verify(cache).setMovieAsNotFavorited(MovieId)
    }

    private fun stubMoviesCacheGetMovies(stub: Flowable<List<MovieEntity>>) {
        whenever(cache.getMovies()) doReturn stub
    }

    private fun stubMoviesCacheSaveMovies(completable: Completable) {
        whenever(cache.saveMovies(any())) doReturn completable
    }

    private fun stubMoviesCacheSetLastCacheTime(completable: Completable) {
        whenever(cache.setLastCacheTime(any())) doReturn completable
    }

    private fun stubMoviesClearMovies(completable: Completable) {
        whenever(cache.clearMovies()) doReturn completable
    }

    private fun stubMoviesCacheGetFavoritedMovies(observable: Observable<List<MovieEntity>>) {
        whenever(cache.getFavoritedMovies()) doReturn observable
    }

    private fun stubMoviesCacheSetMoviesAsFavorited(completable: Completable) {
        whenever(cache.setMovieAsFavorited(any())) doReturn completable
    }

    private fun stubMoviesCacheSetMoviesAsNotFavorited(completable: Completable) {
        whenever(cache.setMovieAsNotFavorited(any())) doReturn completable
    }
}
