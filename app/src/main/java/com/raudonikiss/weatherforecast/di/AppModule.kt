package com.raudonikiss.weatherforecast.di

import android.preference.PreferenceManager
import androidx.room.Room
import com.raudonikiss.weatherforecast.BuildConfig
import com.raudonikiss.weatherforecast.data.AppDatabase
import com.raudonikiss.weatherforecast.network.Webservice
import com.raudonikiss.weatherforecast.viewModels.AddCityViewModel
import com.raudonikiss.weatherforecast.viewModels.CitiesViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

object AppModule{
    val appModule = module {

        val client : OkHttpClient by lazy {
            OkHttpClient.Builder().addInterceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("appid", BuildConfig.WeatherApiKey).build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }.build()
        }

        single {
            Room.databaseBuilder(get(), AppDatabase::class.java, "database").build()
        }
        single {
            retrofit2.Retrofit.Builder()
                .baseUrl(BuildConfig.WeatherApiBase)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Webservice::class.java)
        }
        single {
            PreferenceManager.getDefaultSharedPreferences(get())
        }
        viewModel { CitiesViewModel(get(), get()) }
        viewModel { AddCityViewModel(get(), get()) }
    }

}