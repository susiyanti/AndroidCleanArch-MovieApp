package com.susiyanti.mobileui.injection.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun providesContext(application: Application): Context = application.applicationContext
}
