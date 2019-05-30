package com.raudonikiss.weatherforecast.base

import android.app.Application
import com.raudonikiss.weatherforecast.base.AppModule.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}