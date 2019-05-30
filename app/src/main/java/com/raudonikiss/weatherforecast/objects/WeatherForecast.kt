package com.raudonikiss.weatherforecast.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_forecasts")
data class WeatherForecast(
    val base: String,
    val clouds: Double,
    val code: Int,
    val longtitude: Double,
    val lattitude: Double,
    val datetime: Long,
    @PrimaryKey
    val id: Int,
    val humidity: Double,
    val pressure: Double,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double,
    val city_name: String,
    val country: String,
    val city_id: Int,
    val message: Double,
    val sunrise: Long,
    val sunset: Long,
    val type: Double,
    val visibility: Double,
    val description: String,
    val icon: String,
    val weather_id: Int,
    val main: String,
    val wind_deg: Double,
    val wind_speed: Double
)