package com.evgenii.coolgraph

import android.app.Application
import com.evgenii.coolgraph.di.AppDI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ProjectApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ProjectApplication)
            modules(
                AppDI.appModule,
                AppDI.viewModelModule
            )
        }
    }
}