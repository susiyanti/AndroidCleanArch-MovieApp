package com.susiyanti.movieapp.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.susiyanti.movieapp.cache.db.MovieConstants.DELETE_PROJECTS
import com.susiyanti.movieapp.cache.db.MovieConstants.QUERY_BOOKMARKED_PROJECTS
import com.susiyanti.movieapp.cache.db.MovieConstants.QUERY_EXISTS
import com.susiyanti.movieapp.cache.db.MovieConstants.QUERY_PROJECTS
import com.susiyanti.movieapp.cache.db.MovieConstants.QUERY_UPDATE_BOOKMARK_STATUS
import com.susiyanti.movieapp.cache.model.CachedMovie
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class CachedMoviesDao {

    @Query(QUERY_PROJECTS)
    @JvmSuppressWildcards
    abstract fun getMovies(): Flowable<List<CachedMovie>>

    @Query(QUERY_EXISTS)
    abstract fun cachedMoviesExist(): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertMovies(movies: List<CachedMovie>)

    @Query(DELETE_PROJECTS)
    abstract fun deleteMovies()

    @Query(QUERY_BOOKMARKED_PROJECTS)
    abstract fun getFavoritedMovies(): Flowable<List<CachedMovie>>

    @Query(QUERY_UPDATE_BOOKMARK_STATUS)
    abstract fun setFavoriteStatus(isFavorited: Boolean, projectId: String)
}
