package com.raudonikiss.weatherforecast.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikiss.weatherforecast.objects.WeatherForecast

@Dao
interface WeatherForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherForecast(item : WeatherForecast)

    @Query("SELECT * FROM WEATHER_FORECASTS WHERE city_name=:city_name AND country =:country_id")
    fun getWeatherForecast(city_name : String, country_id : String) : WeatherForecast
}