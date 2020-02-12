package com.susiyanti.movieapp.remote.mapper

import com.susiyanti.movieapp.remote.test.factory.MovieDataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals


@RunWith(JUnit4::class)
class MoviesResponseModelMapperTest {

    private val mapper = MoviesResponseModelMapper()

    @Test fun mapFromModelMapsData() {
        val model = MovieDataFactory.makeMovie()
        val entity = mapper.mapFromModel(model)

        assertEquals(model.id.toString(), entity.id)
        assertEquals(model.title, entity.title)
        assertEquals(model.releaseDate, entity.releaseDate)

        assertEquals(model.poster, entity.poster)
    }
}