package com.susiyanti.movieapp.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.susiyanti.movieapp.cache.db.MovieConstants.COLUMN_IS_BOOKMARKED
import com.susiyanti.movieapp.cache.db.MovieConstants.COLUMN_PROJECT_ID
import com.susiyanti.movieapp.cache.db.MovieConstants.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class CachedMovie(
        @PrimaryKey
        @ColumnInfo(name = COLUMN_PROJECT_ID)
        var id: String,
        var title: String,
        var releaseDate: String,
        var poster: String,
        @ColumnInfo(name = COLUMN_IS_BOOKMARKED)
        var isFavorited: Boolean
)