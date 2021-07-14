package com.ctanas.android.alerts

import android.app.Application
import com.ctanas.android.alerts.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        configureKoin()
    }

    private fun configureKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(appModules)
        }
    }
}