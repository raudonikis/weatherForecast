package com.raudonikiss.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raudonikiss.weatherforecast.objects.WeatherForecast

@Database(
    entities =
    [
        WeatherForecast::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase()
{
    abstract fun weatherForecastDao(): WeatherForecastDao
}