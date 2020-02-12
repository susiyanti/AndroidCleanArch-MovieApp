package com.susiyanti.movieapp.cache.mapper

import com.susiyanti.movieapp.cache.model.CachedMovie
import com.susiyanti.movieapp.cache.test.factory.MovieDataFactory
import com.susiyanti.movieapp.data.model.MovieEntity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class CachedMovieMapperTest {

    private val mapper = CachedMovieMapper()

    @Test fun mapFromCachedMapsData() {
        val model = MovieDataFactory.makeCachedMovie()
        val entity = mapper.mapFromCached(model)

        assertEqualsData(model, entity)
    }

    @Test fun mapToCacheMapsData() {
        val entity = MovieDataFactory.makeMovieEntity()
        val model = mapper.mapToCached(entity)

        assertEqualsData(model, entity)
    }

    private fun assertEqualsData(model: CachedMovie, entity: MovieEntity) {
        assertEquals(model.id, entity.id)
        assertEquals(model.releaseDate, entity.releaseDate)
        assertEquals(model.isFavorited, entity.isFavorited)
        assertEquals(model.title, entity.title)
        assertEquals(model.poster, entity.poster)
    }
}
