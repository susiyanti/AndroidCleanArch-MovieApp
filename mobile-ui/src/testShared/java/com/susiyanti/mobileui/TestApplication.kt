package com.susiyanti.mobileui

import android.app.Activity
import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.susiyanti.mobileui.injection.DaggerTestApplicationComponent
import com.susiyanti.mobileui.injection.TestApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestApplication : Application(), HasActivityInjector {

    companion object {
        fun appComponent(): TestApplicationComponent {
            val testApplication: TestApplication = ApplicationProvider.getApplicationContext()
            return testApplication.appComponent
        }
    }

    @Inject lateinit var injector: DispatchingAndroidInjector<Activity>

    private lateinit var appComponent: TestApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestApplicationComponent.builder().application(this).build()
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }
}