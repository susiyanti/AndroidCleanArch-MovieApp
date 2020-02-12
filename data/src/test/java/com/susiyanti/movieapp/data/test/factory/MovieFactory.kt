package com.susiyanti.movieapp.data.test.factory

import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.domain.model.Movie

internal object MovieFactory {

    fun makeMovieEntity(): MovieEntity {
        return with(DataFactory) {
            MovieEntity(
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomBoolean()
            )
        }
    }

    fun makeMovie(): Movie {
        return with(DataFactory) {
            Movie(
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomBoolean()
            )
        }
    }
}