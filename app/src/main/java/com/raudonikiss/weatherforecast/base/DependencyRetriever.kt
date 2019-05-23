package com.raudonikiss.weatherforecast.base

import android.content.Context
import androidx.room.Room
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.network.ApiUrl
import com.raudonikiss.weatherforecast.repositories.MainRepository

class DependencyRetriever(private val context : Context) {

    val db by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "db").build()
    }

    val webservice: Webservice by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(ApiUrl.API_URL)
            .build()
            .create(Webservice::class.java)
    }

    val repository by lazy {
        MainRepository(webservice, db.cityDao(), db.weatherForecastDao())
    }
}

val Context.dependencyRetriever : DependencyRetriever get() =
    (applicationContext as BaseApplication).dependencyRetriever