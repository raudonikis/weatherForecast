package com.raudonikiss.weatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raudonikiss.weatherforecast.objects.City
import com.raudonikiss.weatherforecast.objects.Weather

@Database(
    entities =
    [
        City::class,
        Weather::class
    ],
    version = 1,
    exportSchema = false
)

abstract class Database : RoomDatabase()
{
    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao

}