package com.susiyanti.mobileui.injection.module

import com.susiyanti.movieapp.data.repository.MoviesRemote
import com.susiyanti.movieapp.remote.MoviesRemoteImpl
import com.susiyanti.movieapp.remote.service.MovieAppService
import com.susiyanti.movieapp.remote.service.MovieAppServiceFactory
import com.susiyanti.mobileui.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideMovieService(): MovieAppService {
        return MovieAppServiceFactory.makeMovieService(BuildConfig.DEBUG)
    }

    @Provides
    @Singleton
    fun provideApiKey(): String {
        return BuildConfig.TMBD_API_KEY
    }

    @Provides
    fun bindMoviesRemote(moviesRemote: MoviesRemoteImpl): MoviesRemote = moviesRemote

}
