package com.raudonikiss.weatherforecast.objects

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class City(
    @PrimaryKey
    val city_id : Long,
    val name : String,
    val country : String?,
    val lon : Double,
    val lat : Double
)