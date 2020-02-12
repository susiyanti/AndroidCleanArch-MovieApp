package com.susiyanti.movieapp.data.mapper

import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.data.test.factory.MovieFactory
import com.susiyanti.movieapp.domain.model.Movie
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MovieMapperTest {

    private val mapper = MovieMapper()

    @Test fun mapFromEntityMapsData() {
        val entity = MovieFactory.makeMovieEntity()
        val model = mapper.mapFromEntity(entity)
        assertEqualData(entity, model)
    }

    @Test fun mapToEntityMapData() {
        val model = MovieFactory.makeMovie()
        val entity = mapper.mapToEntity(model)
        assertEqualData(entity, model)
    }

    private fun assertEqualData(entity: MovieEntity, model: Movie) {
        assertEquals(entity.id, model.id)
        assertEquals(entity.title, model.title)
        assertEquals(entity.releaseDate, model.releaseDate)
        assertEquals(entity.poster, model.poster)
    }

}
