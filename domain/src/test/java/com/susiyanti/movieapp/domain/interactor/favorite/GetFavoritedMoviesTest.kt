package com.susiyanti.movieapp.domain.interactor.favorite

import com.susiyanti.movieapp.domain.executor.PostExecutionThread
import com.susiyanti.movieapp.domain.model.Movie
import com.susiyanti.movieapp.domain.repository.MoviesRepository
import com.susiyanti.movieapp.domain.test.MovieDataFactory
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class GetFavoritedMoviesTest {
    private lateinit var getBookmarkProjects: GetFavoritedMovies
    private val moviesRepository: MoviesRepository = mock()
    private val postExecutionThread: PostExecutionThread = mock()

    @Before fun setup() {
        getBookmarkProjects = GetFavoritedMovies(moviesRepository, postExecutionThread)
    }

    @Test fun getFavoritedMoviesComplete() {
        stubGetMovies(Observable.just(MovieDataFactory.makeMovieList(2)))
        val testObserver = getBookmarkProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test fun getFavoritedMoviesReturnsData() {
        val movies = MovieDataFactory.makeMovieList(2)
        stubGetMovies(Observable.just(movies))
        val testObserver = getBookmarkProjects.buildUseCaseObservable().test()
        testObserver.assertValue(movies)
    }

    private fun stubGetMovies(observable: Observable<List<Movie>>) {
        whenever(moviesRepository.getFavoritedMovies())
                .doReturn(observable)
    }
}