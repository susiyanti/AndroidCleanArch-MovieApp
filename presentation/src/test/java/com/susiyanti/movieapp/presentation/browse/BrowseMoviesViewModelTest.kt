package com.susiyanti.movieapp.presentation.browse

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.susiyanti.movieapp.domain.interactor.favorite.FavoriteMovie
import com.susiyanti.movieapp.domain.interactor.favorite.UnFavoriteMovie
import com.susiyanti.movieapp.domain.interactor.browse.GetMovies
import com.susiyanti.movieapp.domain.model.Movie
import com.susiyanti.movieapp.presentation.BrowseMoviesViewModel
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
import org.mockito.Captor
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BrowseMoviesViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private var getMovies: GetMovies = mock()
    private var favoriteMovie: FavoriteMovie = mock()
    private var unFavoriteMovie: UnFavoriteMovie = mock()
    private var movieMapper: MovieViewMapper = mock()

    private var movieViewModel = BrowseMoviesViewModel(getMovies,
            favoriteMovie, unFavoriteMovie, movieMapper)

    @Captor private val captor = argumentCaptor<DisposableObserver<List<Movie>>>()

    @Test fun fetchMoviesExecutesUseCase() {
        movieViewModel.fetchMovies()
        verify(getMovies, times(1)).execute(any(), eq(null))
    }

    @Test fun fetchMoviesReturnsSuccess() {
        val movies = MovieFactory.makeMovieList(2)
        val movieViews = MovieFactory.makeMovieViewList(2)
        stubMovieMapperMapToView(movieViews[0], movies[0])
        stubMovieMapperMapToView(movieViews[1], movies[1])
        movieViewModel.fetchMovies()
        verify(getMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(movies)
        assertEquals(ResourceState.SUCCESS, movieViewModel.getMovies().value?.status)
    }

    @Test fun fetchMoviesReturnsData() {
        val movies = MovieFactory.makeMovieList(2)
        val movieViews = MovieFactory.makeMovieViewList(2)
        stubMovieMapperMapToView(movieViews[0], movies[0])
        stubMovieMapperMapToView(movieViews[1], movies[1])
        movieViewModel.fetchMovies()
        verify(getMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(movies)
        assertEquals(movieViews, movieViewModel.getMovies().value?.data)
    }

    @Test fun fetchMoviesReturnsError() {
        movieViewModel.fetchMovies()
        verify(getMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())
        assertEquals(ResourceState.ERROR, movieViewModel.getMovies().value?.status)
    }

    @Test fun fetchMoviesReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()
        movieViewModel.fetchMovies()
        verify(getMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))
        assertEquals(errorMessage, movieViewModel.getMovies().value?.message)
    }

    private fun stubMovieMapperMapToView(movieView: MovieView, movie: Movie) {
        whenever(movieMapper.mapToView(movie)) doReturn movieView
    }
}
