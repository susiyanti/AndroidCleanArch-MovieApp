package com.susiyanti.movieapp.cache.db

object MovieConstants {

    const val TABLE_NAME = "movie"

    const val COLUMN_PROJECT_ID = "movie_id"

    const val COLUMN_IS_BOOKMARKED = "is_favorited"

    const val QUERY_PROJECTS = "SELECT * FROM $TABLE_NAME"

    const val QUERY_EXISTS = "SELECT EXISTS(SELECT 1 FROM $TABLE_NAME)"

    const val DELETE_PROJECTS = "DELETE FROM $TABLE_NAME"

    const val QUERY_BOOKMARKED_PROJECTS = "SELECT * FROM $TABLE_NAME " +
            "WHERE $COLUMN_IS_BOOKMARKED = 1"

    const val QUERY_UPDATE_BOOKMARK_STATUS = "UPDATE $TABLE_NAME " +
            "SET $COLUMN_IS_BOOKMARKED = :isFavorited WHERE " +
            "$COLUMN_PROJECT_ID = :projectId"
}
