package com.susiyanti.mobileui.injection

import android.app.Application
import com.susiyanti.movieapp.domain.repository.MoviesRepository
import com.susiyanti.mobileui.TestApplication
import com.susiyanti.mobileui.injection.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    TestApplicationModule::class,
    TestCacheModule::class,
    TestDataModule::class,
    PresentationModule::class,
    UIModule::class,
    TestRemoteModule::class])
interface TestApplicationComponent {

    fun moviesRepository(): MoviesRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestApplicationComponent
    }

    fun inject(application: TestApplication)

}