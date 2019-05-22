package com.raudonikiss.weatherforecast.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikiss.weatherforecast.objects.WeatherForecast

@Dao
interface WeatherForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(item: WeatherForecast)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWeatherData(all: List<WeatherForecast>)

    @Query("DELETE FROM weather_forecasts")
    fun deleteAllWeatherData()

    @Query("SELECT * FROM weather_forecasts")
    fun getAllWeatherData(): List<WeatherForecast>
}