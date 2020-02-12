package com.susiyanti.movieapp.presentation.mapper

import com.susiyanti.movieapp.presentation.test.factory.MovieFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MovieViewMapperTest {

    private val mapper = MovieViewMapper()

    @Test fun mapToViewMapsData() {
        val movie = MovieFactory.makeMovie()
        val movieView = mapper.mapToView(movie)

        assertEquals(movie.id, movieView.id)
        assertEquals(movie.releaseDate, movieView.releaseDate)
        assertEquals(movie.title, movieView.title)
        assertEquals(movie.poster, movieView.poster)
        assertEquals(movie.isFavorited, movieView.isFavorited)
    }

}
