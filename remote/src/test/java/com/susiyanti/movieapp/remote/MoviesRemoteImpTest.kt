package com.susiyanti.movieapp.remote

import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.remote.mapper.MoviesResponseModelMapper
import com.susiyanti.movieapp.remote.model.MovieModel
import com.susiyanti.movieapp.remote.model.MoviesResponseModel
import com.susiyanti.movieapp.remote.service.MovieAppService
import com.susiyanti.movieapp.remote.test.factory.MovieDataFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MoviesRemoteImpTest {

    private val mapper = mock<MoviesResponseModelMapper>()
    private val service = mock<MovieAppService>()
    private val remote = MoviesRemoteImpl(service, mapper,"")

    @Test fun getMoviesCompletes() {
        stubMovieAppServiceLatestMovies(Flowable.just(
                MovieDataFactory.makeMoviesResponse()))
        stubMoviesResponseModelMapperFromModel(any(), MovieDataFactory.makeMovieEntity())

        val testObserver = remote.getMovies().test()
        testObserver.assertComplete()
    }

    @Test fun getMoviesCallServer() {
        stubMovieAppServiceLatestMovies(
                Flowable.just(MovieDataFactory.makeMoviesResponse()))
        stubMoviesResponseModelMapperFromModel(any(), MovieDataFactory.makeMovieEntity())

        remote.getMovies().test()
        verify(service).latestMovies(any())
    }

    @Test fun getMoviesReturnsData() {
        val response = MovieDataFactory.makeMoviesResponse()
        stubMovieAppServiceLatestMovies(Flowable.just(response))
        val entities = mutableListOf<MovieEntity>()
        response.items.forEach {
            val entity = MovieDataFactory.makeMovieEntity()
            entities.add(entity)
            stubMoviesResponseModelMapperFromModel(it, entity)
        }
        val testObserver = remote.getMovies().test()
        testObserver.assertValue(entities)
    }

    @Test fun getMoviesCallsWithCorrectParameter() {
        stubMovieAppServiceLatestMovies(Flowable.just(
                MovieDataFactory.makeMoviesResponse()))
        stubMoviesResponseModelMapperFromModel(any(), MovieDataFactory.makeMovieEntity())

        remote.getMovies().test()
        verify(service).latestMovies("")
    }

    private fun stubMovieAppServiceLatestMovies(stub: Flowable<MoviesResponseModel>) {
        whenever(service.latestMovies(any())) doReturn stub
    }

    private fun stubMoviesResponseModelMapperFromModel(model: MovieModel, entity: MovieEntity) {
        whenever(mapper.mapFromModel(model)) doReturn entity
    }
}