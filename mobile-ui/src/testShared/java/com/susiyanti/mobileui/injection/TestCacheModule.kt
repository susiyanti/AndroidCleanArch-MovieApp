package com.susiyanti.mobileui.injection

import android.app.Application
import com.susiyanti.movieapp.cache.db.MoviesDatabase
import com.susiyanti.movieapp.data.repository.MoviesCache
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestCacheModule {

    @Provides @JvmStatic @Singleton
    fun providesDatabase(application: Application): MoviesDatabase {
        return MoviesDatabase.getInstance(application)
    }

    @Provides @JvmStatic @Singleton
    fun provideProjectsCache(): MoviesCache = mock()
}
