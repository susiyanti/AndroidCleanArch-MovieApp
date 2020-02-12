package com.susiyanti.mobileui.injection

import com.susiyanti.movieapp.domain.repository.MoviesRepository
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides @Singleton @JvmStatic
    fun provideDataRepository(): MoviesRepository = mock()
}
