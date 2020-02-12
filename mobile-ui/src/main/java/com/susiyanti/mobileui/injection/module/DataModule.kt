package com.susiyanti.mobileui.injection.module

import com.susiyanti.movieapp.data.MoviesDataRepository
import com.susiyanti.movieapp.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: MoviesDataRepository): MoviesRepository
}
