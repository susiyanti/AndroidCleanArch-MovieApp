package com.susiyanti.movieapp.domain.interactor.favorite

import com.susiyanti.movieapp.domain.executor.PostExecutionThread
import com.susiyanti.movieapp.domain.repository.MoviesRepository
import com.susiyanti.movieapp.domain.test.MovieDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class UnFavoriteMovieTest {

    private lateinit var unFavoriteMovie: UnFavoriteMovie
    private val moviesRepository: MoviesRepository = mock()
    private val postExecutionThread: PostExecutionThread = mock()

    @Before fun setup() {
        unFavoriteMovie = UnFavoriteMovie(moviesRepository, postExecutionThread)
    }

    @Test fun unfavoriteMovieCompletes() {
        stubUnfavoriteMovie(Completable.complete())
        val testObserver = unFavoriteMovie.buildUseCaseCompletable(
                UnFavoriteMovie.Params.forProject(MovieDataFactory.randomString()))
                .test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun unfavoriteMovieThrowsException() {
        unFavoriteMovie.buildUseCaseCompletable().test()
    }

    private fun stubUnfavoriteMovie(completable: Completable) {
        whenever(moviesRepository.unfavoriteMovie(any())) doReturn completable
    }
}
