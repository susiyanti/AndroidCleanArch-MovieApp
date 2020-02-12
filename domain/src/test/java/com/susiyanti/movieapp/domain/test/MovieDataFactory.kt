@file:Suppress("MemberVisibilityCanBePrivate")

package com.susiyanti.movieapp.domain.test

import com.susiyanti.movieapp.domain.model.Movie
import java.util.*

internal object MovieDataFactory {

    fun randomString() = UUID.randomUUID().toString()
    fun randomBoolean() = Math.random() < 0.5

    fun makeMovie() = Movie(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomBoolean()
    )

    fun makeMovieList(count: Int): List<Movie> {
        val movies = mutableListOf<Movie>()
        repeat(count) { movies.add(makeMovie()) }
        return movies
    }
}