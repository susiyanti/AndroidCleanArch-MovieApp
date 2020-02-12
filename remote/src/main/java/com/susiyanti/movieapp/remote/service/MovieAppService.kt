package com.susiyanti.movieapp.remote.service

import com.susiyanti.movieapp.remote.model.MoviesResponseModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAppService {

    @GET("3/movie/popular")
    fun latestMovies(@Query("api_key") query: String)
            : Flowable<MoviesResponseModel>
}
