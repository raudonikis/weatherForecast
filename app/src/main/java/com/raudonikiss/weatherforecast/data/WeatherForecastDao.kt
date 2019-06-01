package com.raudonikiss.weatherforecast.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import io.reactivex.Flowable

@Dao
interface WeatherForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherForecast(item: WeatherForecast)

    @Query("DELETE FROM weather_forecasts")
    fun removeAllForecasts()

    @Query("DELETE FROM weather_forecasts WHERE city_name=:city_name AND country=:country_id")
    fun removeForecast(city_name: String, country_id: String)

    @Query("SELECT * FROM weather_forecasts WHERE city_name=:city_name AND country =:country_id")
    fun getWeatherForecast(city_name: String, country_id: String): WeatherForecast

    @Query("SELECT * FROM weather_forecasts")
    fun getAllWeatherForecasts(): Flowable<List<WeatherForecast>>

    @Query("SELECT * FROM weather_forecasts ORDER BY datetime")
    fun getWeatherForecasts(): LiveData<List<WeatherForecast>>
}