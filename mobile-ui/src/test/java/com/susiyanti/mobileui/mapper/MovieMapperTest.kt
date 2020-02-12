package com.susiyanti.mobileui.mapper

import com.susiyanti.mobileui.test.factory.MovieFactory
import org.junit.Test
import kotlin.test.assertEquals

class MovieMapperTest {

    private val projectMapper = MovieViewMapper()

    @Test
    fun mapToViewMapsData() {
        val project = MovieFactory.makeMovieView()
        val projectForUi = projectMapper.mapToView(project)

        assertEquals(project.id, projectForUi.id)
        assertEquals(project.releaseDate, projectForUi.releaseDate)
        assertEquals(project.title, projectForUi.title)
        assertEquals(project.poster, projectForUi.poster)
        assertEquals(project.isFavorited, projectForUi.isFavorited)
    }
}
