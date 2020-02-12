package com.susiyanti.movieapp.presentation.mapper

interface Mapper<out V, in D> {
    fun mapToView(type: D): V
}
