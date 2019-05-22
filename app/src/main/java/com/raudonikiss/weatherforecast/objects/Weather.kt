package com.raudonikiss.weatherforecast.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Weather(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val city_id : Long,
    val dateTime : Date,
    val temp : Double,
    val temp_min : Double,
    val temp_max : Double,
    val pressure : Double?,
    val sea_level : Double?,
    val ground_level : Double?,
    val humidity : Int,
    val temp_kf : Double?,
    val weather_id : Long,
    val weather_main : String,
    val weather_description : String,
    val weather_icon : String?,
    val clouds : Int?,
    val wind_speed : Double?,
    val wind_deg : Double?,
    val rain : Double?,
    val snow : Double?,
    val dateTime_txt : Date
)