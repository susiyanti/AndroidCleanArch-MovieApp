package com.susiyanti.movieapp.remote.test.factory

import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.remote.model.MovieModel
import com.susiyanti.movieapp.remote.model.MoviesResponseModel

object MovieDataFactory {

    fun makeMovie(): MovieModel {
        return with(DataFactory) {
            MovieModel(randomInt(), randomString(), randomInt(), randomDouble(), randomBoolean(), randomDouble(),randomString(), randomString(), randomString(), randomString(), randomString(), randomString())
        }
    }

    fun makeMovieEntity(): MovieEntity {
        return with(DataFactory) {
            MovieEntity(randomString(), randomString(), randomString(),
                    randomString(), randomBoolean())
        }
    }

    fun makeMoviesResponse(): MoviesResponseModel {
        return MoviesResponseModel(DataFactory.randomInt(),listOf(makeMovie(), makeMovie()))
    }
}
