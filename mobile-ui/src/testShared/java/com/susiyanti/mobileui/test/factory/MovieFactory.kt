package com.susiyanti.mobileui.test.factory

import com.susiyanti.movieapp.domain.model.Movie
import com.susiyanti.movieapp.presentation.model.MovieView

object MovieFactory {

    fun makeMovieView(): MovieView = with(DataFactory) {
        MovieView(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomBoolean()
        )
    }

    fun makeMovie() = with(DataFactory) {
        Movie(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomBoolean()
        )
    }

    fun makeMovieList(count: Int): List<Movie> {
        return  mutableListOf<Movie>().apply {
            repeat(count) { add(makeMovie()) }
        }
    }
}
