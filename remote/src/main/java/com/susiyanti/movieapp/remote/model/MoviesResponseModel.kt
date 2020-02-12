package com.susiyanti.movieapp.remote.model

import com.google.gson.annotations.SerializedName

class MoviesResponseModel(val page:Int, @SerializedName("results") val items: List<MovieModel>)
