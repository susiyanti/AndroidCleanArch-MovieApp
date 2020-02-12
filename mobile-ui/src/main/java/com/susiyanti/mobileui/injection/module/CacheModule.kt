package com.susiyanti.mobileui.injection.module

import android.content.Context
import com.susiyanti.movieapp.cache.MoviesCacheImpl
import com.susiyanti.movieapp.cache.db.MoviesDatabase
import com.susiyanti.movieapp.data.repository.MoviesCache
import dagger.Module
import dagger.Provides

@Module
class CacheModule {

    @Provides
    fun providesMoviesDatabase(context: Context): MoviesDatabase {
        return MoviesDatabase.getInstance(context)
    }

    @Provides
    fun providesMoviesCache(moviesCache: MoviesCacheImpl): MoviesCache = moviesCache
}
