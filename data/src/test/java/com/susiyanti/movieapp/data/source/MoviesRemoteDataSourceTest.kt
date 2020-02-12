package com.susiyanti.movieapp.data.source

import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.data.repository.MoviesRemote
import com.susiyanti.movieapp.data.test.factory.DataFactory
import com.susiyanti.movieapp.data.test.factory.MovieFactory
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Flowable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class MoviesRemoteDataSourceTest {

    private val remote = mock<MoviesRemote>()
    private val store = MoviesRemoteDataSource(remote)

    @Test fun getMoviesCompletes() {
        stubRemoteGetMovies(Flowable.just(listOf(MovieFactory.makeMovieEntity())))
        val testObserver = store.getMovies().test()
        testObserver.assertComplete()
    }

    @Test fun getMoviesReturnsData() {
        val data = listOf(MovieFactory.makeMovieEntity())
        stubRemoteGetMovies(Flowable.just(data))
        val testObserver = store.getMovies().test()
        testObserver.assertValue(data)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveMoviesThrowsException() {
        store.saveMovies(listOf()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearMoviesThrowsException() {
        store.clearMovies().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getFavoritedMoviesThrowsException() {
        store.getFavoritedMovies().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setMovieAsFavoritedThrowsException() {
        store.setMovieAsFavorited(DataFactory.randomString()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setMovieAsNotFavoritedThrowsException() {
        store.setMovieAsNotFavorited(DataFactory.randomString()).test()
    }

    private fun stubRemoteGetMovies(stub: Flowable<List<MovieEntity>>) {
        whenever(remote.getMovies()) doReturn stub
    }
}
