package com.susiyanti.mobileui.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.susiyanti.movieapp.presentation.BrowseFavoritedMoviesViewModel
import com.susiyanti.movieapp.presentation.BrowseMoviesViewModel
import com.susiyanti.mobileui.injection.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(BrowseMoviesViewModel::class)
    abstract fun bindBrowseMoviesViewModel(viewModel: BrowseMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BrowseFavoritedMoviesViewModel::class)
    abstract fun bindBrowseFavoritedMoviesViewModel(viewModel: BrowseFavoritedMoviesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)