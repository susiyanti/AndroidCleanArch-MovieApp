package com.susiyanti.movieapp.domain.interactor.browse

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
import org.mockito.MockitoAnnotations

class GetMoviesTest {

    private lateinit var getMovies: GetMovies
    private val moviesRepository: MoviesRepository = mock()
    private val postExecutionThread: PostExecutionThread = mock()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getMovies = GetMovies(moviesRepository, postExecutionThread)
    }

    @Test
    fun getMoviesCompletes() {
        stubGetMovies(Observable.just(MovieDataFactory.makeMovieList(2)))
        val testObserver = getMovies.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getMoviesReturnsData() {
        val projects = MovieDataFactory.makeMovieList(2)
        stubGetMovies(Observable.just(projects))
        val testObserver = getMovies.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetMovies(observable: Observable<List<Movie>>) {
        whenever(moviesRepository.getMovies()).doReturn(observable)
    }
}