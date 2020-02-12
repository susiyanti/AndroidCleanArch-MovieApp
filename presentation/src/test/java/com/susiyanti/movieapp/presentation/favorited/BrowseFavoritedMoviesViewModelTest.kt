package com.susiyanti.movieapp.presentation.favorited

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.susiyanti.movieapp.domain.interactor.favorite.GetFavoritedMovies
import com.susiyanti.movieapp.domain.model.Movie
import com.susiyanti.movieapp.presentation.BrowseFavoritedMoviesViewModel
import com.susiyanti.movieapp.presentation.mapper.MovieViewMapper
import com.susiyanti.movieapp.presentation.model.MovieView
import com.susiyanti.movieapp.presentation.state.ResourceState
import com.susiyanti.movieapp.presentation.test.factory.DataFactory
import com.susiyanti.movieapp.presentation.test.factory.MovieFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableObserver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BrowseFavoritedMoviesViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val getFavoritedMovies: GetFavoritedMovies = mock()
    private val mapper: MovieViewMapper = mock()
    private val projectViewModel = BrowseFavoritedMoviesViewModel(getFavoritedMovies, mapper)
    private val captor = argumentCaptor<DisposableObserver<List<Movie>>>()

    @Test fun fetchMoviesExecutesUseCase() {
        projectViewModel.fetchMovies()
        verify(getFavoritedMovies, times(1)).execute(any(), eq(null))
    }

    @Test fun fetMoviesReturnsSuccess() {
        val projects = MovieFactory.makeMovieList(2)
        val projectViews = MovieFactory.makeMovieViewList(2)
        stubMovieMapperMapToView(projects[0], projectViews[0])
        stubMovieMapperMapToView(projects[1], projectViews[1])
        projectViewModel.fetchMovies()
        verify(getFavoritedMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)
        assertEquals(ResourceState.SUCCESS, projectViewModel.getMovies().value?.status)
    }

    @Test fun fetMoviesReturnsData() {
        val projects = MovieFactory.makeMovieList(2)
        val projectViews = MovieFactory.makeMovieViewList(2)
        stubMovieMapperMapToView(projects[0], projectViews[0])
        stubMovieMapperMapToView(projects[1], projectViews[1])
        projectViewModel.fetchMovies()
        verify(getFavoritedMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)
        assertEquals(projectViews, projectViewModel.getMovies().value?.data)
    }

    @Test fun fetMoviesReturnsError() {
        projectViewModel.fetchMovies()
        verify(getFavoritedMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())
        assertEquals(ResourceState.ERROR, projectViewModel.getMovies().value?.status)
    }

    @Test fun fetMoviesReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()
        projectViewModel.fetchMovies()
        verify(getFavoritedMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))
        assertEquals(errorMessage, projectViewModel.getMovies().value?.message)
    }

    private fun stubMovieMapperMapToView(movie: Movie, movieView: MovieView) {
        whenever(mapper.mapToView(movie)) doReturn movieView
    }
}
