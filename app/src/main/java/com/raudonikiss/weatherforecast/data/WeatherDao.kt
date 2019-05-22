package com.raudonikiss.weatherforecast.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikiss.weatherforecast.objects.Weather

interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(item: Weather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWeatherData(all: List<Weather>)

    @Query("DELETE FROM Weather")
    fun deleteAllWeatherData()

    @Query("SELECT * FROM Weather")
    fun getAllWeatherData(): List<Weather>
}