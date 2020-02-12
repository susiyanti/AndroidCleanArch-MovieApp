package com.susiyanti.mobileui.mapper

import com.susiyanti.movieapp.presentation.model.MovieView
import com.susiyanti.mobileui.model.Movie
import javax.inject.Inject

class MovieViewMapper @Inject constructor() : ViewMapper<MovieView, Movie> {
    override fun mapToView(presentation: MovieView): Movie {
        return with(presentation) {
            Movie(id, title,releaseDate, poster, isFavorited)
        }
    }
}