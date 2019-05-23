package com.raudonikiss.weatherforecast.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikiss.weatherforecast.objects.WeatherForecast

@Dao
interface WeatherForecastDao {

    @Query("SELECT * FROM WEATHER_FORECASTS WHERE city_id = :cityId")
    fun getCurrentWeatherForecast(cityId : Long): LiveData<WeatherForecast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherForecast(item: WeatherForecast)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWeatherForecasts(all: List<WeatherForecast>)

    @Query("DELETE FROM weather_forecasts")
    fun deleteAllWeatherForecasts()

    @Query("SELECT * FROM weather_forecasts")
    fun getAllWeatherForecasts(): List<WeatherForecast>
}