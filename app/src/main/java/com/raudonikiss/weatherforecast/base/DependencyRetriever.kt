package com.raudonikiss.weatherforecast.base

import android.content.Context
import androidx.room.Room
import com.raudonikiss.weatherforecast.BuildConfig
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.network.Webservice
import okhttp3.*
import retrofit2.converter.gson.GsonConverterFactory

class DependencyRetriever(private val context : Context) {

    val db by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "db").build()
    }

    private val client : OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor { chain ->
            var request = chain.request()
            val url = request.url().newBuilder().addQueryParameter("appid", BuildConfig.WeatherApiKey).build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }.build()
    }

    val webservice: Webservice by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BuildConfig.WeatherApiBase)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Webservice::class.java)
    }
}

val Context.dependencyRetriever : DependencyRetriever get() =
    (applicationContext as BaseApplication).dependencyRetriever