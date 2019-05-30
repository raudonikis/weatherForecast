package com.raudonikiss.weatherforecast.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import io.reactivex.Flowable

@Dao
interface WeatherForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherForecast(item : WeatherForecast)

    @Query("SELECT * FROM weather_forecasts WHERE city_name=:city_name AND country =:country_id")
    fun getWeatherForecast(city_name : String, country_id : String) : WeatherForecast

    @Query("SELECT * FROM weather_forecasts")
    fun getAllWeatherForecasts(): Flowable<List<WeatherForecast>>

    @Query("SELECT * FROM weather_forecasts")
    fun getWeatherForecasts(): LiveData<List<WeatherForecast>>
}