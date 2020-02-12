@file:Suppress("unused")

package com.susiyanti.mobileui.injection.module

import com.susiyanti.movieapp.domain.executor.PostExecutionThread
import com.susiyanti.mobileui.UIThread
import com.susiyanti.mobileui.favorited.FavoritedActivity
import com.susiyanti.mobileui.browse.BrowseActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeBrowseActivity(): BrowseActivity

    @ContributesAndroidInjector
    abstract fun contributeBookmarkedActivity(): FavoritedActivity
}
