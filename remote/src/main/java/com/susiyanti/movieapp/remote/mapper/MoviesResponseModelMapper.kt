package com.susiyanti.movieapp.remote.mapper

import com.susiyanti.movieapp.data.model.MovieEntity
import com.susiyanti.movieapp.remote.model.MovieModel
import javax.inject.Inject

class MoviesResponseModelMapper @Inject constructor() : ModelMapper<MovieModel, MovieEntity> {

    override fun mapFromModel(model: MovieModel): MovieEntity {
        return with(model) {
            MovieEntity(id.toString(), title, releaseDate,
                    poster,  false)
        }
    }
}
