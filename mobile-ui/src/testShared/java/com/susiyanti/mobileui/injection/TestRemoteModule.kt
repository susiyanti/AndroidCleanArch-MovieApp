package com.susiyanti.mobileui.injection

import com.susiyanti.movieapp.data.repository.MoviesRemote
import com.susiyanti.movieapp.remote.service.MovieAppService
import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestRemoteModule {

    @Provides @JvmStatic @Singleton
    fun provideGithubService(): MovieAppService = mock()

    @Provides @JvmStatic @Singleton
    fun providesProjectsRemote(): MoviesRemote = mock()

}
