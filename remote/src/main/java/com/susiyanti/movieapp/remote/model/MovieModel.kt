package com.susiyanti.movieapp.remote.model

import com.google.gson.annotations.SerializedName

class MovieModel(val id: Int, val title: String,
                 @SerializedName("vote_count") val voteCount: Int,
                 @SerializedName("vote_average") val voteAverage: Double,
                 @SerializedName("adult") val adult: Boolean,
                 @SerializedName("popularity") var popularity: Double ,
                 @SerializedName("release_date") val releaseDate: String,
                 @SerializedName("poster_path") val poster: String,
                 @SerializedName("original_language") val originalLanguage: String,
                 @SerializedName("original_title") val originalTitle: String,
                 @SerializedName("backdrop_path") val backdropPath: String? = null,
                 @SerializedName("overview") val overview: String? = null)
