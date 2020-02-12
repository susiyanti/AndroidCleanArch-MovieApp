package com.susiyanti.movieapp.presentation.mapper

import com.susiyanti.movieapp.domain.model.Movie
import com.susiyanti.movieapp.presentation.model.MovieView
import javax.inject.Inject

class MovieViewMapper @Inject constructor(): Mapper<MovieView, Movie> {
    override fun mapToView(type: Movie): MovieView {
        return with(type) {
            MovieView(
                    id,
                    title,
                    releaseDate,
                    poster,
                    isFavorited
            )
        }
    }
}
