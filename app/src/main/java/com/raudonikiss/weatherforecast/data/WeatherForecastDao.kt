package com.raudonikiss.weatherforecast.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raudonikiss.weatherforecast.objects.WeatherForecast
import io.reactivex.Single

@Dao
interface WeatherForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherForecast(item: WeatherForecast)

    @Delete
    fun deleteForecast(item: WeatherForecast)

    @Query("SELECT COUNT(*) FROM weather_forecasts WHERE city_name=:city_name AND country=:country_id")
    fun countItems(city_name: String, country_id: String): Single<Int>

    @Query("SELECT * FROM weather_forecasts WHERE city_name=:city_name AND country =:country_id")
    fun getWeatherForecast(city_name: String, country_id: String): WeatherForecast

    @Query("SELECT * FROM weather_forecasts")
    fun getWeatherForecasts(): LiveData<List<WeatherForecast>>
}