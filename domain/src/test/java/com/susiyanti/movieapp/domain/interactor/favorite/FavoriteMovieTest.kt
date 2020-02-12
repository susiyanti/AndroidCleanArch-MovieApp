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

class FavoriteMovieTest {

    private lateinit var favoriteMovie: FavoriteMovie
    private val moviesRepository: MoviesRepository = mock()
    private val postExecutionThread: PostExecutionThread = mock()

    @Before fun setup() {
        favoriteMovie = FavoriteMovie(moviesRepository, postExecutionThread)
    }

    @Test fun favoriteMovieCompletes() {
        stubFavoriteMovie(Completable.complete())
        val testObserver = favoriteMovie.buildUseCaseCompletable(
                FavoriteMovie.Params.forProject(MovieDataFactory.randomString())).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun favoriteMovieThrowsException() {
        favoriteMovie.buildUseCaseCompletable().test()
    }

    private fun stubFavoriteMovie(completable: Completable) {
        whenever(moviesRepository.favoriteMovie(any())) doReturn completable
    }
}
