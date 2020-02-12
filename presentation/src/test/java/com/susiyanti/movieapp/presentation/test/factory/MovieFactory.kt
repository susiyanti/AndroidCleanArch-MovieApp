package com.susiyanti.movieapp.presentation.test.factory

import com.susiyanti.movieapp.domain.model.Movie
import com.susiyanti.movieapp.presentation.model.MovieView

object MovieFactory {

    private fun makeMovieView(): MovieView {
        return with(DataFactory) {
            MovieView(
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

    fun makeMovieViewList(count: Int): List<MovieView> {
        val movies = mutableListOf<MovieView>()
        repeat(count) {
            movies.add(makeMovieView())
        }
        return movies
    }

    fun makeMovieList(count: Int): List<Movie> {
        val movies = mutableListOf<Movie>()
        repeat(count) {
            movies.add(makeMovie())
        }
        return movies
    }
}
