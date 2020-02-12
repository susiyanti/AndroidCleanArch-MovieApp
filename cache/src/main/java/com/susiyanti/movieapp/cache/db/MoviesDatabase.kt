package com.susiyanti.movieapp.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.susiyanti.movieapp.cache.dao.CachedMoviesDao
import com.susiyanti.movieapp.cache.dao.ConfigDao
import com.susiyanti.movieapp.cache.model.CachedMovie
import com.susiyanti.movieapp.cache.model.Config
import com.susiyanti.movieapp.cache.model.SingletonHolder

@Database(
        entities = [
            CachedMovie::class,
            Config::class],
        version = 1,
        exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun cachedMoviesDao(): CachedMoviesDao

    abstract fun configDao(): ConfigDao

    companion object : SingletonHolder<Context, MoviesDatabase>({
        Room.databaseBuilder(
                it.applicationContext,
                MoviesDatabase::class.java,
                "project.db"
        ).build()
    })
}
