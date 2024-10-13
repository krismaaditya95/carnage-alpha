package com.snister.carnagealpha.config

import android.app.Application
import com.snister.carnagealpha.core.dependency_injection.mainBindings
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@App)
            modules(
                mainBindings
            )
        }
    }
}