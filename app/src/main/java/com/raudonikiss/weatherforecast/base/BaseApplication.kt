package com.raudonikiss.weatherforecast.base

import android.app.Application

class BaseApplication : Application() {

    lateinit var dependencyRetriever: DependencyRetriever private set

    override fun onCreate() {
        super.onCreate()
        dependencyRetriever = DependencyRetriever(this)
    }
}