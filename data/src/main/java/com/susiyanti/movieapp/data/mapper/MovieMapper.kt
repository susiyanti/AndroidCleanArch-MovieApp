package com.susiyanti.movieapp.data.mapper

import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.domain.model.Movie
import javax.inject.Inject

class MovieMapper @Inject constructor(): EntityMapper<MovieEntity, Movie> {

    override fun mapFromEntity(entity: MovieEntity): Movie {
        return with(entity) {
            Movie(id, title, releaseDate, poster, isFavorited)
        }
    }

    override fun mapToEntity(domain: Movie): MovieEntity {
        return with(domain) {
            MovieEntity(id, title, releaseDate, poster, isFavorited)
        }
    }
}