package com.susiyanti.movieapp.cache.mapper

import com.susiyanti.movieapp.cache.model.CachedMovie
import com.susiyanti.movieapp.data.model.MovieEntity
import javax.inject.Inject

class CachedMovieMapper @Inject constructor(): CacheMapper<CachedMovie, MovieEntity> {

    override fun mapFromCached(type: CachedMovie): MovieEntity {
        return with(type) {
            MovieEntity(id, title, releaseDate, poster, isFavorited)
        }
    }

    override fun mapToCached(type: MovieEntity): CachedMovie {
        return with(type) {
           CachedMovie(id, title, releaseDate, poster, isFavorited)
        }
    }
}
