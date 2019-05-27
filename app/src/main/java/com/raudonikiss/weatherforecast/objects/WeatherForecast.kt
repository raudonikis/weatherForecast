package com.raudonikiss.weatherforecast.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_forecasts")
data class WeatherForecast(
    val base: String,
    val clouds: Int,
    val code: Int,
    val longtitude: Double,
    val lattitude: Double,
    val datetime: Int,
    @PrimaryKey
    val id: Int,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double,
    val city_name: String,
    val country: String,
    val city_id: Int,
    val message: Double,
    val sunrise: Int,
    val sunset: Int,
    val type: Int,
    val visibility: Int,
    val description: String,
    val icon: String,
    val weather_id: Int,
    val main: String,
    val wind_deg: Double,
    val wind_speed: Double
)