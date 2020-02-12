package com.susiyanti.movieapp.remote

import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.data.repository.MoviesRemote
import com.susiyanti.movieapp.remote.mapper.MoviesResponseModelMapper
import com.susiyanti.movieapp.remote.service.MovieAppService
import io.reactivex.Flowable
import javax.inject.Inject


class MoviesRemoteImpl @Inject constructor(
        private val service: MovieAppService,
        private val mapper: MoviesResponseModelMapper,
        private val API_KEY: String
) : MoviesRemote {


    override fun getMovies(): Flowable<List<MovieEntity>> {
        return service.latestMovies(API_KEY)
                .map { response -> response.items.map { mapper.mapFromModel(it) } }
    }
}
