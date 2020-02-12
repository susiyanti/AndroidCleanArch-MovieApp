package com.susiyanti.movieapp.data.repository

import com.susiyanti.movieapp.data.model.MovieEntity
import io.reactivex.Flowable

interface MoviesRemote {

    fun getMovies(): Flowable<List<MovieEntity>>
}
